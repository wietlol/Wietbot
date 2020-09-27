package me.wietlol.wietbot.commands.evaljavascript.core.api

import com.amazonaws.services.lambda.AWSLambda
import com.amazonaws.services.lambda.model.InvocationType.RequestResponse
import com.amazonaws.services.lambda.model.InvokeRequest
import com.amazonaws.services.lambda.runtime.events.SNSEvent
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.loggo.api.Logger
import me.wietlol.loggo.common.CommonLog
import me.wietlol.loggo.common.EventId
import me.wietlol.utils.common.ByteWrapper
import me.wietlol.utils.json.lambda.LambdaException
import me.wietlol.wietbot.commands.evaljavascript.core.api.models.EvalRequest
import me.wietlol.wietbot.commands.evaljavascript.core.api.models.EvalResponse
import me.wietlol.wietbot.commands.evaljavascript.core.setup.DependencyInjection
import me.wietlol.wietbot.data.commands.models.models.CommandCall
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.BaseHandler
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.FunctionEventIdSet
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.lambdaFunction
import me.wietlol.wietbot.services.chatclient.models.ChatClientService
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequest
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequestImpl
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.qualifier.named

class EvalJavascriptHandler : KoinComponent, BaseHandler
{
	init
	{
		DependencyInjection.bindServiceCollection()
	}
	
	val lambdaClient: AWSLambda = get()
	val chatClient: ChatClientService = get()
	val mapper: ObjectMapper = get()
	
	override val requestSchema: Schema = get(named("commandSchema"))
	override val responseSchema: Schema = get(named("chatClientSchema"))
	override val logger: Logger<CommonLog> = get()
	
	private val evalJavascriptEventIds = FunctionEventIdSet(
		EventId(1280687644, "evalJavascript-request"),
		EventId(2059229516, "evalJavascript-response"),
		EventId(1931560132, "evalJavascript-error"),
	)
	
	fun evalJavascriptSns(request: SNSEvent) =
		request
			.records
			?.singleOrNull()
			?.sns
			?.message
			?.let { mapper.readValue<ByteWrapper>(it) }
			?.let { lambdaFunction(it, evalJavascriptEventIds, ::evalJavascript) }
	
	private fun evalJavascript(request: CommandCall): SendMessageRequest
	{
		val result = kotlin.runCatching { invokeEvalJavascriptPrivate(request.argumentText) }
			.getOrElse { "${it.javaClass.name}(${it.message})" }
		
		return SendMessageRequestImpl(
			request.message.source.id,
			result
		).also { chatClient.sendMessage(it) }
	}
	
	private fun invokeEvalJavascriptPrivate(code: String): String
	{
		val request = EvalRequest(code)
		val response = lambdaClient.invoke(InvokeRequest()
			.withFunctionName("wietbot-commands-eval-javascript-dev-evalJavascriptPrivate")
			.withInvocationType(RequestResponse)
			.withPayload(mapper.writeValueAsString(request))
		)
		
		if (response.functionError != null)
			throw mapper.readValue(response.payload.array(), LambdaException::class.java)
		
		val evalResponse = mapper.readValue<EvalResponse>(response.payload.array())
		
		return evalResponse.result
	}
}
