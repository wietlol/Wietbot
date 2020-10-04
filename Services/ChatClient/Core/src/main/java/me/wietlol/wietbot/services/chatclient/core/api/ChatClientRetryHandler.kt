package me.wietlol.wietbot.services.chatclient.core.api

import com.amazonaws.services.lambda.runtime.events.SNSEvent
import com.fasterxml.jackson.databind.ObjectMapper
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.loggo.api.Logger
import me.wietlol.loggo.common.CommonLog
import me.wietlol.loggo.common.EventId
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.BaseEventHandler
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.FunctionEventIdSet
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.lambdaFunction
import me.wietlol.wietbot.services.chatclient.core.interfaces.ChatRetryService
import me.wietlol.wietbot.services.chatclient.core.interfaces.PlatformResolver
import me.wietlol.wietbot.services.chatclient.core.setup.DependencyInjection
import me.wietlol.wietbot.services.chatclient.models.models.EditMessageResponse
import me.wietlol.wietbot.services.chatclient.models.models.EditMessageResponseImpl
import me.wietlol.wietbot.services.chatclient.models.models.EditMessageRetryRequest
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageResponse
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageResponseImpl
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRetryRequest
import org.koin.core.KoinComponent
import org.koin.core.get

class ChatClientRetryHandler : KoinComponent, BaseEventHandler
{
	init
	{
		DependencyInjection.bindServiceCollection()
	}
	
	private val chatRetryService: ChatRetryService = get()
	private val platformResolver: PlatformResolver = get()
	
	override val logger: Logger<CommonLog> = get()
	override val mapper: ObjectMapper = get()
	override val requestSchema: Schema = get()
	override val responseSchema: Schema = requestSchema
	
	private val sendMessageEventIds = FunctionEventIdSet(
		EventId(1900268921, "sendMessage-request"),
		EventId(1196124041, "sendMessage-response"),
		EventId(1010024273, "sendMessage-error"),
	)
	private val editMessageEventIds = FunctionEventIdSet(
		EventId(1350341321, "editMessage-request"),
		EventId(1265252816, "editMessage-response"),
		EventId(1603838919, "editMessage-error"),
	)
	
	fun sendMessageSns(request: SNSEvent) =
		lambdaFunction(request, sendMessageEventIds, ::sendMessage)
	
	private fun sendMessage(request: SendMessageRetryRequest): SendMessageResponse =
		try
		{
			val platform = platformResolver.getByName(request.request.platform)
			platform.sendMessage(request.request)
			SendMessageResponseImpl()
		}
		catch (ex: Throwable)
		{
			if (request.tryCount < 10)
			{
				chatRetryService.retrySendMessage(request.request, request.tryCount + 1)
				SendMessageResponseImpl()
			}
			else
				throw ex
		}
	
	fun editMessageSns(request: SNSEvent) =
		lambdaFunction(request, editMessageEventIds, ::editMessage)
	
	private fun editMessage(request: EditMessageRetryRequest): EditMessageResponse =
		try
		{
			val platform = platformResolver.getByName(request.request.platform)
			platform.editMessage(request.request)
			EditMessageResponseImpl()
		}
		catch (ex: Throwable)
		{
			if (request.tryCount < 10)
			{
				chatRetryService.retryEditMessage(request.request, request.tryCount + 1)
				EditMessageResponseImpl()
			}
			else
				throw ex
		}
}
