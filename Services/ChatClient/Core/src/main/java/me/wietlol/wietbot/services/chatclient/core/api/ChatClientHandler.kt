package me.wietlol.wietbot.services.chatclient.core.api

import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.loggo.api.Logger
import me.wietlol.loggo.common.CommonLog
import me.wietlol.loggo.common.EventId
import me.wietlol.utils.common.ByteWrapper
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.BaseHandler
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.FunctionEventIdSet
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.lambdaFunction
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.SeChatClient
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.SeChatClientFactory
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.SeCredentials
import me.wietlol.wietbot.services.chatclient.core.setup.DependencyInjection
import me.wietlol.wietbot.services.chatclient.models.ChatClientService
import me.wietlol.wietbot.services.chatclient.models.models.EditMessageRequest
import me.wietlol.wietbot.services.chatclient.models.models.EditMessageResponse
import me.wietlol.wietbot.services.chatclient.models.models.EditMessageResponseImpl
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequest
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageResponse
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageResponseImpl
import org.koin.core.KoinComponent
import org.koin.core.get

class ChatClientHandler : KoinComponent, ChatClientService, BaseHandler
{
	init
	{
		DependencyInjection.bindServiceCollection()
	}
	
	private val chatClientFactory: SeChatClientFactory = get()
	private val credentials: SeCredentials = get()
	private var chatClient: SeChatClient = chatClientFactory.create(credentials)
	
	override val requestSchema: Schema = get()
	override val responseSchema: Schema = requestSchema
	override val logger: Logger<CommonLog> = get()
	
	private val sendMessageEventIds = FunctionEventIdSet(
		EventId(1220909344, "sendMessage-request"),
		EventId(1717808799, "sendMessage-response"),
		EventId(1585545767, "sendMessage-error"),
	)
	private val editMessageEventIds = FunctionEventIdSet(
		EventId(1267151029, "editMessage-request"),
		EventId(1832470963, "editMessage-response"),
		EventId(1764820660, "editMessage-error"),
	)
	
	fun sendMessageBit(request: ByteWrapper): ByteWrapper? =
		lambdaFunction(request, sendMessageEventIds, ::sendMessage)
	
	override fun sendMessage(request: SendMessageRequest): SendMessageResponse =
		invoke {
			val id = chatClient.sendMessage(request.roomId, request.text)
			SendMessageResponseImpl(id)
		}
	
	fun editMessageBit(request: ByteWrapper): ByteWrapper? =
		lambdaFunction(request, editMessageEventIds, ::editMessage)
	
	override fun editMessage(request: EditMessageRequest): EditMessageResponse =
		invoke {
			chatClient.editMessage(request.messageId, request.text)
			EditMessageResponseImpl()
		}
	
	private fun <R> invoke(action: () -> R): R =
		try
		{
			action()
		}
		catch (ex: Throwable)
		{
			chatClient = chatClientFactory.create(credentials)
			action()
		}
}
