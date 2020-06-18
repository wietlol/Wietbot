package me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient

import me.wietlol.serialization.JsonSerializer2

class HttpSeChatClientFactory(
	val serializer: JsonSerializer2,
	val logger: (String) -> Unit = {}
) : SeChatClientFactory
{
	override fun create(credentials: SeCredentials): HttpSeChatClient
	{
		val cookieJar = mutableMapOf<String, String>()
		
		val mainFKey = getMainFKey(cookieJar)
		
		login(mainFKey, credentials.emailAddress, credentials.password, cookieJar)
		
		val accountFKey = getAccountFKey(cookieJar)
		
		return HttpSeChatClient(chatSiteUrl, accountFKey, cookieJar, serializer, logger)
	}
}

