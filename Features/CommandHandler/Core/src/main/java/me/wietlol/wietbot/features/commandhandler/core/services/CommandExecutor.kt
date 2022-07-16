package me.wietlol.wietbot.features.commandhandler.core.services

import com.amazonaws.services.sns.AmazonSNS
import edu.gatech.gtri.bktree.BkTree
import edu.gatech.gtri.bktree.BkTreeSearcher
import edu.gatech.gtri.bktree.BkTreeSearcher.Match
import edu.gatech.gtri.bktree.MutableBkTree
import jdk.javadoc.internal.doclets.formats.html.markup.ContentBuilder
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.konfig.api.Konfig
import me.wietlol.konfig.api.get
import me.wietlol.loggo.common.CommonLogger
import me.wietlol.loggo.common.EventId
import me.wietlol.loggo.common.logInformation
import me.wietlol.loggo.common.logTrace
import me.wietlol.utils.json.SimpleJsonSerializer
import me.wietlol.wietbot.data.auth.models.AuthService
import me.wietlol.wietbot.data.auth.models.models.GetOrCreateUserRequestImpl
import me.wietlol.wietbot.data.auth.models.models.IsUserAuthorizedRequestImpl
import me.wietlol.wietbot.data.auth.models.models.UserImpl
import me.wietlol.wietbot.data.commands.models.CommandService
import me.wietlol.wietbot.data.commands.models.models.CommandCall
import me.wietlol.wietbot.data.commands.models.models.ListCommandsRequestImpl
import me.wietlol.wietbot.data.messages.models.dsl.ContentBuilder
import me.wietlol.wietbot.data.messages.models.models.Content
import me.wietlol.wietbot.data.messages.models.models.MessageEvent
import me.wietlol.wietbot.data.messages.models.models.MessageEventList
import me.wietlol.wietbot.features.commandhandler.core.interfaces.CommandCallParser
import me.wietlol.wietbot.features.commandhandler.core.interfaces.MessageFilter
import me.wietlol.wietbot.features.commandhandler.core.models.BotCommand
import me.wietlol.wietbot.features.commandhandler.core.utils.DamerauLevenshteinMetric
import me.wietlol.wietbot.services.chatclient.models.ChatClientService
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequestImpl
import kotlin.math.max
import kotlin.math.min

@Suppress("MemberVisibilityCanBePrivate")
class CommandExecutor(
	val snsClient: AmazonSNS,
	val serializer: SimpleJsonSerializer,
	konfig: Konfig,
	val commandService: CommandService,
	val authService: AuthService,
	val chatClient: ChatClientService,
	val schema: Schema,
	val commandCallParser: CommandCallParser,
	val logger: CommonLogger,
	val messageFilter: MessageFilter,
)
{
	private val commandReceivedEventId = EventId(1357661769, "command-received")
	private val commandExecutedEventId = EventId(1840839112, "command-executed")
	private val commandRejectedEventId = EventId(1495730559, "command-rejected")
	private val commandUnknownEventId = EventId(857027356, "command-unknown")
	private val commandAmbiguousEventId = EventId(806812699, "command-ambiguous")
	
	private val matchDistance: Int = konfig.get("commandExecute.fuzzySearch.matchDistance")
	private val suggestDistanceLimit: Int = konfig.get("commandExecute.fuzzySearch.suggestDistanceLimit")
	private val listCommandsKeyword: String = "listCommands".toLowerCase()

	private val commands: List<BotCommand> = getCommands()
	
	private val commandsMap: Map<String, BotCommand> = createCommandsMap(commands)
	
	private val commandWords: BkTree<String> = createCommandWords(commands)
	
	private fun getCommands(): List<BotCommand> =
		commandService.listCommands(ListCommandsRequestImpl()).commands
			.map { BotCommand(it, snsClient, schema, serializer) }
	
	private fun createCommandsMap(commands: List<BotCommand>): Map<String, BotCommand> =
		commands
			.map { Pair(it.keyword.toLowerCase(), it) }
			.toMap()
	
	private fun createCommandWords(commands: List<BotCommand>): BkTree<String> =
		DamerauLevenshteinMetric()
			.let { MutableBkTree<String>(it) }
			.apply {
				addAll(
					commands
						.map { it.keyword.toLowerCase() }
						.plus(listCommandsKeyword)
				)
			}
	
	fun invoke(message: MessageEventList)
	{
		val event = message.events.last() as MessageEvent
		// skip not applicable events
		if (messageFilter.apply(event).not())
			return
		
		val commandCall: CommandCall? = commandCallParser.parseCommand(message)

		if (commandCall != null)
		{
			logger.logInformation(commandReceivedEventId, mapOf(
				"message" to "received command",
				"command" to commandCall
			))
			
			val searcher: BkTreeSearcher<String> = BkTreeSearcher(commandWords)
			val matches: MutableSet<Match<out String>> = searcher.search(commandCall.commandKeyword.toLowerCase(), matchDistance)
			
			val fullMatch: Match<out String>? = matches.singleOrNull()
				?: matches.singleOrNull { it.match == commandCall.commandKeyword }
			
			when
			{
				fullMatch != null -> handleCommand(event, commandCall, fullMatch)
				matches.isEmpty() -> handleNoCommands(event, commandCall, searcher)
				else -> handleTooManyCommands(event, matches)
			}
		}
	}
	
	private fun handleCommand(message: MessageEvent, commandCall: CommandCall, fullMatch: Match<out String>)
	{
		logger.logTrace(commandExecutedEventId, mapOf(
			"message" to "processing command",
			"fullMatch" to fullMatch
		))
		
		ensureUserExists(message.user.id, message.platform, message.user.name)
		
		// 'listcommands' is a special case and cannot be handled outside of this application
		// we can also skip the authorization process
		if (fullMatch.match == listCommandsKeyword)
		{
			sendMessage(
				message.platform,
				message.source.id,
				ContentBuilder.content {
					text(commands.joinToString { it.keyword })
				}
			)
			return
		}
		
		val realCommand: BotCommand = commandsMap.getValue(fullMatch.match)
		if (isUserAuthorized(message.user.id, message.platform, realCommand.keyword))
		{
			logger.logTrace(commandExecutedEventId, mapOf(
				"message" to "running command"
			))
			
			realCommand.execute(commandCall)
		}
		else
		{
			logger.logTrace(commandRejectedEventId, mapOf(
				"message" to "not authorized to run command"
			))
			
			sendMessage(
				message.platform,
				message.source.id,
				ContentBuilder.content {
					text("@${message.user.name.replace(" ", "")} You are not allowed to use the '${fullMatch.match}' command.")
				}
			)
		}
	}
	
	private fun handleNoCommands(message: MessageEvent, commandCall: CommandCall, searcher: BkTreeSearcher<String>)
	{
		logger.logTrace(commandUnknownEventId, mapOf(
			"message" to "unknown command"
		))
		
		val suggestDistance = max(1, min(suggestDistanceLimit, commandCall.commandKeyword.length / 2))
		val suggestions: MutableSet<Match<out String>> = searcher.search(commandCall.commandKeyword, suggestDistance)
		
		if (suggestions.isEmpty())
			sendMessage(
				message.platform,
				message.source.id,
				ContentBuilder.content {
					text("I have no clue what you meant right there. You can use the ")
					mono("listCommands")
					text(" command to see all my commands.")
				}
			)
		else
			sendMessage(
				message.platform,
				message.source.id,
				ContentBuilder.content {
					text("I don't know this command, did you mean any of the following? ${suggestions.joinToString { it.match }}")
				}
			)
	}
	
	private fun handleTooManyCommands(message: MessageEvent, matches: MutableSet<Match<out String>>)
	{
		logger.logTrace(commandAmbiguousEventId, mapOf(
			"message" to "ambiguous command",
			"matches" to matches
		))
		
		sendMessage(
			message.platform,
			message.source.id,
			ContentBuilder.content {
				text("I don't know which command to choose from. ${matches.joinToString { it.match }}")
			}
		)
	}
	
	private fun ensureUserExists(userId: String, userName: String, platform: String)
	{
		authService.getOrCreateUser(GetOrCreateUserRequestImpl(UserImpl(
			userId,
			userName,
			platform
		)))
	}
	
	private fun isUserAuthorized(userId: String, platform: String, command: String): Boolean
	{
		val response = authService.isUserAuthorized(IsUserAuthorizedRequestImpl(
			userId,
			platform,
			"command-execute",
			command
		))
		
		return response.isAuthorized
	}
	
	private fun sendMessage(platform: String, target: String, content: Content)
	{
		chatClient.sendMessage(SendMessageRequestImpl(platform, target, content))
	}
}
