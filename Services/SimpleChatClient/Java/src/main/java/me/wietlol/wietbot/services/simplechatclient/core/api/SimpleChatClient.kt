package me.wietlol.wietbot.services.simplechatclient.core.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import me.wietlol.serialization.JacksonSerializerAdapter
import me.wietlol.serialization.JsonSerializer2
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.HttpSeChatClientFactory
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.SeChatClient
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.SeCredentials
import me.wietlol.wietbot.services.simplechatclient.core.api.models.EditMessageRequest
import me.wietlol.wietbot.services.simplechatclient.core.api.models.SendMessageRequest

class SimpleChatClient
{
	private val clientFactory = HttpSeChatClientFactory(buildJsonSerializer())
	
	private lateinit var client: SeChatClient
	
	private val credentials = SeCredentials(
		System.getenv("username"),
		System.getenv("password")
	)
	
	fun sendMessage(request: SendMessageRequest)
	{
		callStackOverflowChat {
			it.sendMessage(request.roomId, request.messageText)
		}
	}
	
	fun editMessage(request: EditMessageRequest)
	{
		callStackOverflowChat {
			it.editMessage(request.messageId, request.messageText)
		}
	}
	
	private fun callStackOverflowChat(action: (SeChatClient) -> Unit)
	{
		if (::client.isInitialized.not())
			client = clientFactory.create(credentials)
		
		try
		{
			action(client)
		}
		catch (ex: Exception)
		{
			client = clientFactory.create(credentials)
			action(client)
		}
	}
	
	private fun buildJsonSerializer(): JsonSerializer2 = ObjectMapper()
		.also { it.registerModule(KotlinModule()) }
		.let { JacksonSerializerAdapter(it) }
}
