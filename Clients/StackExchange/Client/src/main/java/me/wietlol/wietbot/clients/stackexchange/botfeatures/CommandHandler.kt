package me.wietlol.wietbot.clients.stackexchange.botfeatures

import com.amazonaws.services.sns.AmazonSNS
import edu.gatech.gtri.bktree.BkTree
import edu.gatech.gtri.bktree.BkTreeSearcher
import edu.gatech.gtri.bktree.BkTreeSearcher.Match
import edu.gatech.gtri.bktree.MutableBkTree
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.konfig.api.Konfig
import me.wietlol.konfig.api.get
import me.wietlol.loggo.api.Logger
import me.wietlol.loggo.api.LoggerFactory
import me.wietlol.loggo.common.CommonLog
import me.wietlol.loggo.common.EventId
import me.wietlol.loggo.common.logError
import me.wietlol.loggo.common.logInformation
import me.wietlol.reactor.filteredBy
import me.wietlol.reactor.plus
import me.wietlol.serialization.SimpleJsonSerializer
import me.wietlol.wietbot.clients.stackexchange.botfeatures.commands.BotCommand
import me.wietlol.wietbot.clients.stackexchange.botfeatures.commands.ExternalCommand
import me.wietlol.wietbot.clients.stackexchange.botfeatures.commands.internalCommands
import me.wietlol.wietbot.clients.stackexchange.util.DamerauLevenshteinMetric
import me.wietlol.wietbot.data.auth.models.AuthService
import me.wietlol.wietbot.data.auth.models.models.GetOrCreateUserRequest
import me.wietlol.wietbot.data.auth.models.models.IsUserAuthorizedRequest
import me.wietlol.wietbot.data.commands.models.CommandService
import me.wietlol.wietbot.data.commands.models.models.CommandCall
import me.wietlol.wietbot.data.commands.models.models.ListCommandsRequest
import me.wietlol.wietbot.data.commands.models.models.Message
import me.wietlol.wietbot.data.commands.models.models.Room
import me.wietlol.wietbot.data.commands.models.models.User
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.SeChatEvents
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.SeWebSocketClient
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.MessageEdited
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.MessageEvent
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.MessagePosted
import me.wietlol.wietbot.services.chatclient.models.ChatClientService
import kotlin.math.max
import kotlin.math.min

@Suppress("MemberVisibilityCanBePrivate")
class CommandHandler(
	val webSocketClient: SeWebSocketClient,
	chatEvents: SeChatEvents,
	val snsClient: AmazonSNS,
	val serializer: SimpleJsonSerializer,
	konfig: Konfig,
	val commandService: CommandService,
	val authService: AuthService,
	val chatClient: ChatClientService,
	val schema: Schema,
	val loggerFactory: LoggerFactory<CommonLog>
)
{
	private val commandReceivedEventId = EventId(1357661769, "command-received")
	private val commandExecutedEventId = EventId(1840839112, "command-executed")
	private val commandRejectedEventId = EventId(1495730559, "command-rejected")
	private val commandUnknownEventId = EventId(857027356, "command-unknown")
	private val commandAmbiguousEventId = EventId(806812699, "command-ambiguous")
	private val commandHandlerErrorEventId = EventId(1569967345, "command-handler-error")
	
	private val prefix: String = konfig.get<String>("commandExecute.prefix").toLowerCase()
	private val matchDistance: Int = konfig.get("commandExecute.fuzzySearch.matchDistance")
	private val suggestDistanceLimit: Int = konfig.get("commandExecute.fuzzySearch.suggestDistanceLimit")
	
	var commands: List<BotCommand>
		private set
	
	private var commandsMap: Map<String, BotCommand>
	
	private var commandWords: BkTree<String>
	
	init
	{
		commands = getAllCommands()
		commandsMap = createCommandsMap(commands)
		commandWords = createCommandWords(commands)
	}
	
	fun refreshCommands()
	{
		commands = getAllCommands()
		commandsMap = createCommandsMap(commands)
		commandWords = createCommandWords(commands)
	}
	
	private fun getAllCommands(): List<BotCommand> =
		getExternalCommands() + internalCommands
	
	private fun getExternalCommands(): List<BotCommand> =
		commandService.listCommands(ListCommandsRequest.of()).commands
			.map { ExternalCommand(it, snsClient, schema, serializer) }
	
	private fun createCommandsMap(commands: List<BotCommand>): Map<String, BotCommand> =
		commands
			.map { Pair(it.keyword.toLowerCase(), it) }
			.toMap()
	
	private fun createCommandWords(commands: List<BotCommand>): BkTree<String> =
		DamerauLevenshteinMetric()
			.let { MutableBkTree<String>(it) }
			.apply { addAll(commands.map { it.keyword.toLowerCase() }) }
	
	private val staticIgnoredUsers = listOf(11_345_663)
	
	init
	{
		(chatEvents.onMessagePosted + chatEvents.onMessageEdited)
			.filteredBy { staticIgnoredUsers.contains(it.userId).not() }
			.filteredBy { it.content.toLowerCase().startsWith("$prefix ") }
			.register { event ->
				loggerFactory.createLogger().use { logger ->
					try
					{
						val commandCall: CommandCall? = parseCommand(event)
						
						if (commandCall != null)
						{
							logger.logInformation(commandReceivedEventId, mapOf(
								"message" to "received command",
								"command" to commandCall
							))
							
							val searcher: BkTreeSearcher<String> = BkTreeSearcher(commandWords)
							val matches: MutableSet<Match<out String>> = searcher.search(commandCall.commandKeyword.toLowerCase(), matchDistance)
							
							val fullMatch = matches.singleOrNull()
								?: matches.singleOrNull { it.match == commandCall.commandKeyword }
							
							when
							{
								fullMatch != null ->
								{
									logger.logInformation(commandExecutedEventId, mapOf(
										"message" to "processing command",
										"fullMatch" to fullMatch
									))
									
									ensureUserExists(event.userId, event.userName)
									
									val realCommand = commandsMap.getValue(fullMatch.match)
									if (isUserAuthorized(event.userId, realCommand.keyword))
									{
										logger.logInformation(commandExecutedEventId, mapOf(
											"message" to "running command"
										))
										
										// execute command
										realCommand.execute(this, commandCall)
									}
									else
									{
										logger.logInformation(commandRejectedEventId, mapOf(
											"message" to "not authorized to run command"
										))
										
										sendMessage(event.roomId, "@${event.userName.replace(" ", "")} You are not allowed to use the '${fullMatch.match}' command.")
									}
								}
								matches.isEmpty() ->
								{
									logger.logInformation(commandUnknownEventId, mapOf(
										"message" to "unknown command"
									))
									
									// no commands found
									val suggestDistance = max(1, min(suggestDistanceLimit, commandCall.commandKeyword.length / 2))
									val suggestions = searcher.search(commandCall.commandKeyword, suggestDistance)
									
									if (suggestions.isEmpty())
										sendMessage(event.roomId, "I have no clue what you meant right there. You can use the `listCommands` command to see all my commands.")
									else
										sendMessage(event.roomId, "I don't know this command, did you mean any of the following? ${suggestions.joinToString { it.match }}")
								}
								else ->
								{
									logger.logInformation(commandAmbiguousEventId, mapOf(
										"message" to "ambiguous command",
										"matches" to matches
									))
									
									// too many fuzzy matched commands found
									sendMessage(event.roomId, "I don't know which command to choose from. ${matches.joinToString { it.match }}")
								}
							}
						}
					}
					catch (ex: Exception)
					{
						logger.logError(commandHandlerErrorEventId, mapOf<String, Any>(), ex)
						throw ex
					}
				}
			}
	}
	
	private fun parseCommand(event: MessageEvent): CommandCall?
	{
		val pattern = "^(?:(?i)${prefix}) ([a-zA-Z0-9_-]+) ?(?s)(.*)\$".toRegex()
		
		val match = pattern.matchEntire(event.content) ?: return null
		
		return CommandCall.of(
			match.groupValues[1],
			match.groupValues[2],
			Message.of(
				when (event)
				{
					is MessagePosted -> event.messageId
					is MessageEdited -> event.messageId
					else -> event.id
				},
				User.of(event.userId, event.userName),
				event.content,
				Room.of(event.roomId)
			)
		)
	}
	
	private fun ensureUserExists(userId: Int, userName: String)
	{
		authService.getOrCreateUser(GetOrCreateUserRequest.of(me.wietlol.wietbot.data.auth.models.models.User.of(
			userId,
			userName
		)))
	}
	
	private fun isUserAuthorized(userId: Int, command: String): Boolean
	{
		val response = authService.isUserAuthorized(IsUserAuthorizedRequest.of(
			userId,
			"command-execute",
			command
		))
		
		return response.isAuthorized
	}
	
	private fun sendMessage(roomId: Int, text: String)
	{
		chatClient.sendMessage(me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequest.of(roomId, text))
	}
}
