package me.wietlol.wietbot.clients.stackexchange

import com.amazonaws.services.sns.AmazonSNS
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.konfig.api.Konfig
import me.wietlol.loggo.api.LoggerFactory
import me.wietlol.loggo.common.CommonLog
import me.wietlol.loggo.common.CommonLogger
import me.wietlol.loggo.common.EventId
import me.wietlol.loggo.common.logError
import me.wietlol.serialization.SimpleJsonSerializer
import me.wietlol.wietbot.clients.stackexchange.botfeatures.CommandHandler
import me.wietlol.wietbot.data.auth.models.AuthService
import me.wietlol.wietbot.data.commands.models.CommandService
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.SeChatEvents
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.SeWebSocketClient
import me.wietlol.wietbot.services.chatclient.models.ChatClientService
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequest
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.PrintWriter
import java.io.StringWriter

class Wietbot : KoinComponent
{
	private val logger: CommonLogger by inject()
	private val webSocketClient: SeWebSocketClient by inject()
	private val chatEvents: SeChatEvents by inject()
	private val serializer: SimpleJsonSerializer by inject()
	private val konfig: Konfig by inject()
	private val chatClient: ChatClientService by inject()
	private val schema: Schema by inject()
	private val snsClient: AmazonSNS by inject()
	private val commandService: CommandService by inject()
	private val authService: AuthService by inject()
	private val loggerFactory: LoggerFactory<CommonLog> by inject()
	
	fun start()
	{
		try
		{
			webSocketClient // make sure it has a connection
			
			registerBotFeatures()

			chatClient.sendMessage(SendMessageRequest.of(1, "ohai"))
		}
		catch (ex: Exception)
		{
			ex.printStackTrace()
		}
	}
	
	private fun registerBotFeatures()
	{
		try
		{
			CommandHandler(webSocketClient, chatEvents, snsClient, serializer, konfig, commandService, authService, chatClient, schema, loggerFactory)
		}
		catch (ex: Exception)
		{
			webSocketClient.close()
			
			logger.logError(EventId(1569653573, "temp"), mapOf<String, Any>(), ex)
		}
	}
}
