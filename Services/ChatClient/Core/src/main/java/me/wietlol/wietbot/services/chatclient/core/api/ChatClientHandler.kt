package me.wietlol.wietbot.services.chatclient.core.api

import me.wietlol.aws.lambda.LambdaRequest
import me.wietlol.aws.lambda.LambdaResponse
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.deserialize
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.SeChatClient
import me.wietlol.wietbot.services.chatclient.core.interfaces.ChatRetryService
import me.wietlol.wietbot.services.chatclient.core.setup.DependencyInjection
import me.wietlol.wietbot.services.chatclient.models.ChatClientService
import me.wietlol.wietbot.services.chatclient.models.models.EditMessageRequest
import me.wietlol.wietbot.services.chatclient.models.models.EditMessageResponse
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequest
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageResponse
import org.koin.core.KoinComponent
import org.koin.core.get

class ChatClientHandler : KoinComponent, ChatClientService
{
	init
	{
		DependencyInjection.bindServiceCollection()
	}
	
	val chatClient: SeChatClient = get()
	val schema: Schema = get()
	val chatRetryService: ChatRetryService = get()
	
	fun sendMessageBit(request: LambdaRequest): LambdaResponse? =
		request
			.payload
			?.let { schema.deserialize<SendMessageRequest>(it) }
			?.let { sendMessage(it) }
			?.let { schema.serialize(it) }
			?.let { LambdaResponse(it) }
	
	override fun sendMessage(request: SendMessageRequest): SendMessageResponse
	{
		try
		{
			val id = chatClient.sendMessage(request.roomId, request.text)

			return SendMessageResponse.of(id)
		}
		catch (ex: Throwable)
		{
			chatRetryService.retrySendMessage(request, 1)
			throw ex
		}
	}
	
	fun editMessageBit(request: LambdaRequest): LambdaResponse? =
		request
			.payload
			?.let { schema.deserialize<EditMessageRequest>(it) }
			?.let { editMessage(it) }
			?.let { schema.serialize(it) }
			?.let { LambdaResponse(it) }
	
	override fun editMessage(request: EditMessageRequest): EditMessageResponse
	{
		try
		{
			chatClient.editMessage(request.messageId, request.text)
			
			return EditMessageResponse.of()
		}
		catch (ex: Throwable)
		{
			chatRetryService.retryEditMessage(request, 1)
			throw ex
		}
	}
}
