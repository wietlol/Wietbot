package me.wietlol.wietbot.commands.evalsql.core.api

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
import me.wietlol.wietbot.commands.evalsql.core.api.models.EvalRequest
import me.wietlol.wietbot.commands.evalsql.core.api.models.EvalResponse
import me.wietlol.wietbot.commands.evalsql.core.setup.DependencyInjection
import me.wietlol.wietbot.data.commands.models.models.CommandCall
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.BaseEventHandler
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.BaseHandler
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.FunctionEventIdSet
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.lambdaFunction
import me.wietlol.wietbot.services.chatclient.models.ChatClientService
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequest
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequestImpl
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.qualifier.named

class EvalSqlHandler : KoinComponent, BaseEventHandler
{
	init
	{
		DependencyInjection.bindServiceCollection()
	}
	
	private val lambdaClient: AWSLambda = get()
	private val chatClient: ChatClientService = get()
	
	override val requestSchema: Schema = get(named("commandSchema"))
	override val responseSchema: Schema = get(named("chatClientSchema"))
	override val logger: Logger<CommonLog> = get()
	override val mapper: ObjectMapper = get()
	
	private val evalSqlEventIds = FunctionEventIdSet(
		EventId(1087801770, "evalSql-request"),
		EventId(1080720235, "evalSql-response"),
		EventId(1010429273, "evalSql-error"),
	)
	
	fun evalSqlSns(request: SNSEvent) =
		lambdaFunction(request, evalSqlEventIds, ::evalSql)
	
	private fun evalSql(request: CommandCall): SendMessageRequest
	{
		val result = kotlin.runCatching { invokeEvalSqlPrivate(request.argumentText) }
			.getOrElse { "${it.javaClass.name}(${it.message})" }
		
		return SendMessageRequestImpl(
			request.message.source.id,
			result
		).also { chatClient.sendMessage(it) }
	}
	
	private fun invokeEvalSqlPrivate(code: String): String
	{
		val request = EvalRequest(code)
		
		val response = lambdaClient.invoke(InvokeRequest()
			.withFunctionName("wietbot-commands-eval-sql-dev-evalSqlPrivate")
			.withInvocationType(RequestResponse)
			.withPayload(mapper.writeValueAsString(request))
		)
		
		if (response.functionError != null)
			throw mapper.readValue<LambdaException>(response.payload.array())
		
		val evalResponse = mapper.readValue<EvalResponse>(response.payload.array())
		
		return evalResponse.result
	}
}
