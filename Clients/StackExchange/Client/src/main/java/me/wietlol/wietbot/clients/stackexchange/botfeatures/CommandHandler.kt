package me.wietlol.wietbot.clients.stackexchange.botfeatures

import com.amazonaws.services.sns.AmazonSNS
import edu.gatech.gtri.bktree.BkTree
import edu.gatech.gtri.bktree.BkTreeSearcher
import edu.gatech.gtri.bktree.MutableBkTree
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.konfig.api.Konfig
import me.wietlol.konfig.api.get
import me.wietlol.reactor.filteredBy
import me.wietlol.reactor.plus
import me.wietlol.serialization.JsonSerializer2
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

class CommandHandler(
	val webSocketClient: SeWebSocketClient,
	val chatEvents: SeChatEvents,
	val snsClient: AmazonSNS,
	val serializer: JsonSerializer2,
	val konfig: Konfig,
	val commandService: CommandService,
	val authService: AuthService,
	val chatClient: ChatClientService,
	val schema: Schema
)
{
	private val prefix: String = konfig.get<String>("commandExecute.prefix").toLowerCase()
	private val matchDistance: Int = konfig.get("commandExecute.fuzzySearch.matchDistance")
	private val suggestDistanceLimit: Int = konfig.get("commandExecute.fuzzySearch.suggestDistanceLimit")
	
	private val externalCommands: List<BotCommand> = commandService.listCommands(ListCommandsRequest.of()).commands
		.map { ExternalCommand(it, snsClient, schema, serializer) }
	
	val commands: List<BotCommand> = externalCommands + internalCommands
	
	private val commandsMap = commands
		.map { Pair(it.keyword.toLowerCase(), it) }
		.toMap()
	
	private val commandWords: BkTree<String> = DamerauLevenshteinMetric()
		.let { MutableBkTree<String>(it) }
		.apply { addAll(commands.map { it.keyword.toLowerCase() }) }
	
	private val staticIgnoredUsers = listOf(11_345_663)
	
	init
	{
		(chatEvents.onMessagePosted + chatEvents.onMessageEdited)
			.filteredBy { staticIgnoredUsers.contains(it.userId).not() }
			.filteredBy { it.content.toLowerCase().startsWith("$prefix ") }
			.register { event ->
				try
				{
					val commandCall: CommandCall? = parseCommand(event)
					
					if (commandCall != null)
					{
						println("received command '${commandCall.commandKeyword}'")
						
						val searcher = BkTreeSearcher(commandWords)
						val matches = searcher.search(commandCall.commandKeyword.toLowerCase(), matchDistance)
						
						val fullMatch = matches.singleOrNull()
							?: matches.singleOrNull { it.match == commandCall.commandKeyword }
						
						when
						{
							fullMatch != null ->
							{
								println("processing command '${fullMatch.match}'")
								
								ensureUserExists(event.userId, event.userName)
								
								val realCommand = commandsMap.getValue(fullMatch.match)
								if (isUserAuthorized(event.userId, realCommand.keyword))
								{
									println("running command '${fullMatch.match}'")
									
									// execute command
									realCommand.execute(this, commandCall)
								}
								else
								{
									println("not authorized")
									
									sendMessage(event.roomId, "@${event.userName.replace(" ", "")} You are not allowed to use the '${fullMatch.match}' command.")
								}
							}
							matches.isEmpty() ->
							{
								println("unknown")
								
								// no commands found
								val suggestDistance = max(1, min(suggestDistanceLimit, commandCall.commandKeyword.length / 2))
								val suggestions = searcher.search(commandCall.commandKeyword, suggestDistance)
								
								if (suggestions.isEmpty())
									sendMessage(event.roomId, "I have no clue what you meant right there.")
								else
									sendMessage(event.roomId, "I don't know this command, did you mean any of the following? ${suggestions.joinToString { it.match }}")
							}
							else ->
							{
								println("ambiguous")
								
								// too many fuzzy matched commands found
								sendMessage(event.roomId, "I don't know which command to choose from. ${matches.joinToString { it.match }}")
							}
						}
					}
				}
				catch (ex: Exception)
				{
					ex.printStackTrace()
					throw ex
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
