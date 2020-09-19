package me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient

import me.wietlol.loggo.common.CommonLogger
import me.wietlol.loggo.common.EventId
import me.wietlol.loggo.common.ScopedSourceLogger
import me.wietlol.loggo.common.logInformation
import me.wietlol.utils.json.SimpleJsonSerializer

class HttpSeChatClientFactory(
	val serializer: SimpleJsonSerializer,
	val logger: CommonLogger
) : SeChatClientFactory
{
	private val loginEventId = EventId(2145437065, "logging-in")
	
	private val selfLogger = ScopedSourceLogger(logger) { it + "HttpSeChatClientFactory" }
	
	override fun create(credentials: SeCredentials): HttpSeChatClient
	{
		selfLogger.logInformation(loginEventId, mapOf(
			"username" to credentials.emailAddress
		))
		
		val cookieJar = mutableMapOf<String, String>()
		
		val mainFKey = getMainFKey(cookieJar)
		
		login(mainFKey, credentials.emailAddress, credentials.password, cookieJar)
		
		val accountFKey = getAccountFKey(cookieJar)
		
		return HttpSeChatClient(chatSiteUrl, accountFKey, cookieJar, serializer, logger)
	}
}

