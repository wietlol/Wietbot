package me.wietlol.wietbot.commands.evalkotlin.core.api

import com.amazonaws.services.lambda.AWSLambda
import com.amazonaws.services.lambda.model.InvocationType.RequestResponse
import com.amazonaws.services.lambda.model.InvokeRequest
import com.amazonaws.services.lambda.runtime.events.SNSEvent
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import me.wietlol.aws.lambda.LambdaException
import me.wietlol.aws.lambda.LambdaRequest
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.deserialize
import me.wietlol.wietbot.commands.evalkotlin.core.api.models.EvalRequest
import me.wietlol.wietbot.commands.evalkotlin.core.api.models.EvalResponse
import me.wietlol.wietbot.commands.evalkotlin.core.setup.DependencyInjection
import me.wietlol.wietbot.data.commands.models.models.CommandCall
import me.wietlol.wietbot.services.chatclient.models.ChatClientService
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequest
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.qualifier.named

class EvalKotlinHandler : KoinComponent
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
	
	fun evalKotlinSns(request: SNSEvent) =
		request
			.records
			?.single()
			?.sns
			?.message
			?.let { mapper.readValue<LambdaRequest>(it) }
			?.payload
			?.let { commandSchema.deserialize<CommandCall>(it) }
			?.let { evalKotlin(it) }
			?.let { chatClientSchema.serialize(it) }
			?.let { LambdaRequest(it) }
	
	private fun evalKotlin(request: CommandCall): SendMessageRequest
	{
		println("executing code: ${request.argumentText}")
		val result = kotlin.runCatching { invokeEvalKotlinPrivate(request.argumentText) }
			.getOrElse { "${it.javaClass.name}(${it.message})" }
		println("evaluation result: $result")
		
		return SendMessageRequest.of(
			request.message.source.id,
			":${request.message.id} $result"
		)
			.also {
				println("sending message to room ${request.message.source.id}...")
				chatClient.sendMessage(it)
				println("sent message")
			}
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