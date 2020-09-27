package me.wietlol.wietbot.commands.evalnode.core.api

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
import me.wietlol.loggo.common.logInformation
import me.wietlol.utils.common.ByteWrapper
import me.wietlol.utils.json.lambda.LambdaException
import me.wietlol.wietbot.commands.evalnode.core.api.models.EvalRequest
import me.wietlol.wietbot.commands.evalnode.core.api.models.EvalResponse
import me.wietlol.wietbot.commands.evalnode.core.setup.DependencyInjection
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

class EvalNodeHandler : KoinComponent, BaseHandler
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
	
	private val evalNodeSnsEventIds = FunctionEventIdSet(
		EventId(616917887, "evalNode-request"),
		EventId(952010550, "evalNode-response"),
		EventId(577441111, "evalNode-error"),
	)
	
	fun evalNodeSns(request: SNSEvent) =
		request
			.records
			?.singleOrNull()
			?.sns
			?.message
			?.let { mapper.readValue<ByteWrapper>(it) }
			?.let { lambdaFunction(it, evalNodeSnsEventIds, ::evalNode) }
	
	private val resultEventId = EventId(715252279, "evalNode-result")
	
	private fun evalNode(request: CommandCall): SendMessageRequest
	{
		val result = runCatching { invokeEvalNodePrivate(request.argumentText) }
			.getOrElse { "${it.javaClass.name}(${it.message})" }
		logger.logInformation(resultEventId, mapOf("result" to result))
		
		return SendMessageRequestImpl(
			request.message.source.id,
			":${request.message.id} $result"
		).also { chatClient.sendMessage(it) }
	}
	
	private fun invokeEvalNodePrivate(code: String): String
	{
		val request = EvalRequest(code)
		
		val response = lambdaClient.invoke(InvokeRequest()
			.withFunctionName("wietbot-commands-eval-node-2-dev-evalNodePrivate")
			.withInvocationType(RequestResponse)
			.withPayload(mapper.writeValueAsString(request))
		)
		
		if (response.functionError != null)
			throw mapper.readValue<LambdaException>(response.payload.array())
		
		val evalResponse = mapper.readValue<EvalResponse>(response.payload.array())
		
		return evalResponse.result
	}
}
