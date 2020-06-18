package me.wietlol.wietbot.commands.evalcsharp.core.api

import com.amazonaws.services.lambda.AWSLambda
import com.amazonaws.services.lambda.model.InvocationType.RequestResponse
import com.amazonaws.services.lambda.model.InvokeRequest
import com.amazonaws.services.lambda.runtime.events.SNSEvent
import com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.readValue
import me.wietlol.aws.lambda.LambdaException
import me.wietlol.aws.lambda.LambdaExceptionDeserializer
import me.wietlol.aws.lambda.LambdaRequest
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.deserialize
import me.wietlol.wietbot.commands.evalcsharp.core.api.models.EvalRequest
import me.wietlol.wietbot.commands.evalcsharp.core.api.models.EvalResponse
import me.wietlol.wietbot.commands.evalcsharp.core.setup.DependencyInjection
import me.wietlol.wietbot.data.commands.models.models.CommandCall
import me.wietlol.wietbot.services.chatclient.models.ChatClientService
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequest
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject
import org.koin.core.qualifier.named

class EvalCSharpHandler : KoinComponent
{
	init
	{
		DependencyInjection.bindServiceCollection()
	}
	
	val lambdaClient: AWSLambda = get()
	val commandSchema: Schema = get(named("commandSchema"))
	val chatClientSchema: Schema = get(named("chatClientSchema"))
	val chatClient: ChatClientService = get()
	val mapper: ObjectMapper = get()
	
	fun evalCSharpSns(request: SNSEvent) =
		request
			.records
			?.single()
			?.sns
			?.message
			?.let { mapper.readValue<LambdaRequest>(it) }
			?.payload
			?.let { commandSchema.deserialize<CommandCall>(it) }
			?.let { evalCSharp(it) }
			?.let { chatClientSchema.serialize(it) }
			?.let { LambdaRequest(it) }
	
	private fun evalCSharp(request: CommandCall): SendMessageRequest
	{
		println("executing code: ${request.argumentText}")
		val result = runCatching { invokeEvalCSharpPrivate(request.argumentText) }
			.getOrElse { "${it.javaClass.name}(${it.message})" }
		println("evaluation result: $result")
		
		return SendMessageRequest.of(
			request.message.source.id,
			result
		)
			.also {
				println("sending message to room ${request.message.source.id}...")
				chatClient.sendMessage(it)
				println("sent message")
			}
	}
	
	private fun invokeEvalCSharpPrivate(code: String): String
	{
		val request = EvalRequest(code)
		
		val response = lambdaClient.invoke(InvokeRequest()
			.withFunctionName("wietbot-commands-eval-csharp-2-dev-evalCSharpPrivate")
			.withInvocationType(RequestResponse)
			.withPayload(mapper.writeValueAsString(request))
		)
		
		if (response.functionError != null)
			throw mapper.readValue<LambdaException>(response.payload.array())
		
		val evalResponse = mapper.readValue<EvalResponse>(response.payload.array())
		
		return evalResponse.result
	}
}