package me.wietlol.wietbot.commands.evalkotlin.core.api

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
import me.wietlol.wietbot.commands.evalkotlin.core.api.models.EvalRequest
import me.wietlol.wietbot.commands.evalkotlin.core.api.models.EvalResponse
import me.wietlol.wietbot.commands.evalkotlin.core.setup.DependencyInjection
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

class EvalKotlinHandler : KoinComponent, BaseHandler
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

	private val evalKotlinEventIds = FunctionEventIdSet(
		EventId(1057346237, "evalKotlin-request"),
		EventId(1563420999, "evalKotlin-response"),
		EventId(1216429408, "evalKotlin-error"),
	)
	
	fun evalKotlinSns(request: SNSEvent) =
		request
			.records
			?.single()
			?.sns
			?.message
			?.let { mapper.readValue<ByteWrapper>(it) }
			?.let { lambdaFunction(it, evalKotlinEventIds, ::evalKotlin) }
	
	private fun evalKotlin(request: CommandCall): SendMessageRequest
	{
		val result = kotlin.runCatching { invokeEvalKotlinPrivate(request.argumentText) }
			.getOrElse { "${it.javaClass.name}(${it.message})" }
		
		return SendMessageRequestImpl(
			request.message.source.id,
			":${request.message.id} $result"
		).also { chatClient.sendMessage(it) }
	}
	
	private fun invokeEvalKotlinPrivate(code: String): String
	{
		val request = EvalRequest(code)
		
		val response = lambdaClient.invoke(InvokeRequest()
			.withFunctionName("wietbot-commands-eval-kotlin-dev-evalKotlinPrivate")
			.withInvocationType(RequestResponse)
			.withPayload(mapper.writeValueAsString(request))
		)
		
		if (response.functionError != null)
			throw mapper.readValue<LambdaException>(response.payload.array())
		
		val evalResponse = mapper.readValue<EvalResponse>(response.payload.array())
		
		return evalResponse.result
	}
}
