package me.wietlol.wietbot.services.chatclient.client

import com.amazonaws.services.lambda.AWSLambda
import com.amazonaws.services.lambda.model.InvocationType.RequestResponse
import com.amazonaws.services.lambda.model.InvokeRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.deserialize
import me.wietlol.bitblock.core.BitBlockBase
import me.wietlol.bitblock.core.BitSchemaBuilder
import me.wietlol.utils.common.ByteWrapper
import me.wietlol.utils.json.lambda.LambdaException
import me.wietlol.utils.json.lambda.LambdaExceptionDeserializer
import me.wietlol.wietbot.services.chatclient.models.ChatClientService
import me.wietlol.wietbot.services.chatclient.models.WietbotServicesChatClient
import me.wietlol.wietbot.services.chatclient.models.models.EditMessageRequest
import me.wietlol.wietbot.services.chatclient.models.models.EditMessageResponse
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequest
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageResponse

class ChatClientClient(
	val lambdaClient: AWSLambda,
	val serializer: Schema = BitSchemaBuilder.buildSchema(
		WietbotServicesChatClient::class.java.getResourceAsStream("/me/wietlol/wietbot/services/chatclient/models/Api.bitschema"),
		listOf(BitBlockBase, WietbotServicesChatClient),
	),
	val functionPrefix: String = "wietbot-services-chat-client-dev-"
) : ChatClientService
{
	override fun sendMessage(request: SendMessageRequest): SendMessageResponse =
		invoke("sendMessage", request)
	
	override fun editMessage(request: EditMessageRequest): EditMessageResponse =
		invoke("editMessage", request)
	
	private val mapper = ObjectMapper()
		.also { mapper ->
			mapper.registerModule(
				SimpleModule()
					.apply { addDeserializer(LambdaException::class.java, LambdaExceptionDeserializer(mapper)) }
			)
		}
	
	private inline fun <reified Response : Any> invoke(function: String, request: Any): Response
	{
		val lambdaRequest = ByteWrapper(
			serializer.serialize(request)
		)
		
		val response = lambdaClient.invoke(InvokeRequest()
			.withFunctionName(functionPrefix + function)
			.withInvocationType(RequestResponse)
			.withPayload(mapper.writeValueAsString(lambdaRequest))
		)
		
		if (response.functionError != null)
			throw mapper.readValue(response.payload.array(), LambdaException::class.java)
		
		val lambdaResponse = mapper.readValue(response.payload.array(), ByteWrapper::class.java)
		
		return serializer.deserialize(lambdaResponse.payload!!)!!
	}
}
