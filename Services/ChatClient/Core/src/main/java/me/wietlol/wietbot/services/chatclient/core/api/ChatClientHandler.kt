package me.wietlol.wietbot.services.chatclient.core.api

import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.loggo.api.Logger
import me.wietlol.loggo.common.CommonLog
import me.wietlol.loggo.common.EventId
import me.wietlol.utils.common.ByteWrapper
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.BaseHandler
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.FunctionEventIdSet
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.lambdaFunction
import me.wietlol.wietbot.services.chatclient.core.interfaces.ChatRetryService
import me.wietlol.wietbot.services.chatclient.core.interfaces.PlatformResolver
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
	
	private val chatRetryService: ChatRetryService = get()
	private val platformResolver: PlatformResolver = get()
	
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
		try
		{
			val platform = platformResolver.getByName(request.platform)
			platform.sendMessage(request)
			SendMessageResponseImpl()
		}
		catch (ex: Throwable)
		{
			chatRetryService.retrySendMessage(request, 0)
			SendMessageResponseImpl()
		}
	
	fun editMessageBit(request: ByteWrapper): ByteWrapper? =
		lambdaFunction(request, editMessageEventIds, ::editMessage)
	
	override fun editMessage(request: EditMessageRequest): EditMessageResponse =
		try
		{
			val platform = platformResolver.getByName(request.platform)
			platform.editMessage(request)
			EditMessageResponseImpl()
		}
		catch (ex: Throwable)
		{
			chatRetryService.retryEditMessage(request, 0)
			EditMessageResponseImpl()
		}
}
