package me.wietlol.wietbot.services.chatclient.core.api

import com.amazonaws.services.lambda.runtime.events.SNSEvent
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.deserialize
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.SeChatClient
import me.wietlol.wietbot.services.chatclient.core.interfaces.ChatRetryService
import me.wietlol.wietbot.services.chatclient.core.setup.DependencyInjection
import me.wietlol.wietbot.services.chatclient.models.models.EditMessageResponse
import me.wietlol.wietbot.services.chatclient.models.models.EditMessageRetryRequest
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageResponse
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRetryRequest
import org.koin.core.KoinComponent
import org.koin.core.get
import java.util.*

class ChatClientRetryHandler : KoinComponent
{
	init
	{
		DependencyInjection.bindServiceCollection()
	}
	
	val chatClient: SeChatClient = get()
	val schema: Schema = get()
	val chatRetryService: ChatRetryService = get()
	
	fun sendMessageSns(request: SNSEvent) =
		request
			.records
			?.singleOrNull()
			?.sns?.message
			?.let { Base64.getDecoder().decode(it) }
			?.let { schema.deserialize<SendMessageRetryRequest>(it) }
			?.let { sendMessage(it) }
	
	private fun sendMessage(request: SendMessageRetryRequest): SendMessageResponse
	{
		try
		{
			val id = chatClient.sendMessage(request.request.roomId, request.request.text)
			
			return SendMessageResponse.of(id)
		}
		catch (ex: Throwable)
		{
			if (request.tryCount < 10)
				chatRetryService.retrySendMessage(request.request, request.tryCount + 1)
			throw ex
		}
	}
	
	fun editMessageSns(request: SNSEvent) =
		request.records
			?.singleOrNull()
			?.sns?.message
			?.let { Base64.getDecoder().decode(it) }
			?.let { schema.deserialize<EditMessageRetryRequest>(it) }
			?.let { editMessage(it) }
	
	private fun editMessage(request: EditMessageRetryRequest): EditMessageResponse
	{
		try
		{
			chatClient.editMessage(request.request.messageId, request.request.text)
			
			return EditMessageResponse.of()
		}
		catch (ex: Throwable)
		{
			if (request.tryCount < 10)
				chatRetryService.retryEditMessage(request.request, request.tryCount + 1)
			throw ex
		}
	}
}
