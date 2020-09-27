package me.wietlol.wietbot.data.commands.client

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
import me.wietlol.wietbot.data.commands.models.CommandService
import me.wietlol.wietbot.data.commands.models.WietbotDataCommands
import me.wietlol.wietbot.data.commands.models.models.CreateCommandRequest
import me.wietlol.wietbot.data.commands.models.models.CreateCommandResponse
import me.wietlol.wietbot.data.commands.models.models.ListCommandsRequest
import me.wietlol.wietbot.data.commands.models.models.ListCommandsResponse
import me.wietlol.wietbot.data.commands.models.models.RemoveCommandRequest
import me.wietlol.wietbot.data.commands.models.models.RemoveCommandResponse

class CommandClient(
	val lambdaClient: AWSLambda,
	val serializer: Schema = BitSchemaBuilder.buildSchema(
		WietbotDataCommands::class.java.getResourceAsStream("/me/wietlol/wietbot/data/commands/models/Api.bitschema"),
		listOf(BitBlockBase, WietbotDataCommands),
	),
	val functionPrefix: String = "wietbot-data-commands-dev-"
) : CommandService
{
	override fun createCommand(request: CreateCommandRequest): CreateCommandResponse =
		invoke("createCommand", request)
	
	override fun removeCommand(request: RemoveCommandRequest): RemoveCommandResponse =
		invoke("removeCommand", request)
	
	override fun listCommands(request: ListCommandsRequest): ListCommandsResponse =
		invoke("listCommands", request)
	
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
