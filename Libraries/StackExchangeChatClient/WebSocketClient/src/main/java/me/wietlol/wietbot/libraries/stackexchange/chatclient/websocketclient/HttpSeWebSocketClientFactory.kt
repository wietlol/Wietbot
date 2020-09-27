package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient

import me.wietlol.loggo.common.CommonLogger
import me.wietlol.loggo.common.EventId
import me.wietlol.loggo.common.ScopedSourceLogger
import me.wietlol.loggo.common.logInformation
import me.wietlol.utils.json.SimpleJsonSerializer
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.HttpSeChatClient
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.SeCredentials
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.chatSiteUrl
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.getAccountFKey
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.getMainFKey
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.login

class HttpSeWebSocketClientFactory(
	val listener: WebSocketListener,
	val chatEvents: SeChatEvents,
	val serializer: SimpleJsonSerializer,
	val initialRoom: Int,
	val logger: CommonLogger,
	val reconnectCheckInterval: Long,
) : SeWebSocketClientFactory
{
	private val loginEventId = EventId(2145437065, "logging-in")
	
	private val selfLogger = ScopedSourceLogger(logger) { it + "HttpSeWebSocketClientFactory" }
	
	override fun create(credentials: SeCredentials): HttpSeWebSocketClient
	{
		selfLogger.logInformation(loginEventId, mapOf(
			"username" to credentials.emailAddress
		))
		
		val cookieJar = mutableMapOf<String, String>()
		
		val mainFKey = getMainFKey(cookieJar)
		
		login(mainFKey, credentials.emailAddress, credentials.password, cookieJar)
		
		val accountFKey = getAccountFKey(cookieJar)
		
		val client = HttpSeChatClient(chatSiteUrl, accountFKey, cookieJar, serializer, logger)
		return HttpSeWebSocketClient(client, chatSiteUrl, accountFKey, cookieJar, listener, chatEvents, serializer, initialRoom, logger, reconnectCheckInterval)
	}
}
