package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient

import me.wietlol.serialization.JsonSerializer2
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.HttpSeChatClient
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.SeCredentials
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.chatSiteUrl
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.getAccountFKey
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.getMainFKey
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.login
import java.net.CookieManager
import java.net.CookiePolicy

class HttpSeWebSocketClientFactory(
	val listener: WebSocketListener,
	val chatEvents: SeChatEvents,
	val serializer: JsonSerializer2,
	val logger: (String) -> Unit = {}
) : SeWebSocketClientFactory
{
	override fun create(credentials: SeCredentials): HttpSeWebSocketClient
	{
		val cookieJar = mutableMapOf<String, String>()
		
		val mainFKey = getMainFKey(cookieJar)
		
		login(mainFKey, credentials.emailAddress, credentials.password, cookieJar)
		
		val accountFKey = getAccountFKey(cookieJar)
		
		val client = HttpSeChatClient(chatSiteUrl, accountFKey, cookieJar, serializer, logger)
		return HttpSeWebSocketClient(client, chatSiteUrl, accountFKey, cookieJar, listener, chatEvents, serializer)
	}
}
