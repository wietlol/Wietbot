package me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient

import me.wietlol.serialization.JsonSerializer2
import me.wietlol.serialization.deserialize
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.models.SeSendMessageResponse

class HttpSeChatClient(
	private val chatSiteUrl: String,
	private val accountFKey: String,
	private val cookieJar: Map<String, String>,
	private val serializer: JsonSerializer2,
	private val logger: (String) -> Unit
) : SeChatClient
{
	override fun sendMessage(roomId: Int, text: String): Int
	{
		val start = System.currentTimeMillis()
		
		val response = khttp.post(
			"$chatSiteUrl/chats/$roomId/messages/new",
			data = mapOf(
				"text" to text,
				"fkey" to accountFKey
			),
			cookies = cookieJar
		)
		logger("${System.currentTimeMillis() - start}ms sent message to stack exchange")
		
		val json = response.text
		println("sendMessage message: $text")
		println("sendMessage response: $json")
		val sendMessageResponse = serializer.deserialize<SeSendMessageResponse>(json)
		
		logger("${System.currentTimeMillis() - start}ms read and deserialized response")
		return sendMessageResponse.id
	}
	
	override fun editMessage(messageId: Int, text: String)
	{
		khttp.post(
			"$chatSiteUrl/messages/$messageId",
			data = mapOf(
				"text" to text,
				"fkey" to accountFKey
			),
			cookies = cookieJar
		)
	}
}
