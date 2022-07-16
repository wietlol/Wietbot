package me.wietlol.wietbot.features.commandhandler.core

import com.amazonaws.regions.Regions.EU_WEST_1
import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.AmazonSNSClientBuilder
import com.amazonaws.services.sns.model.PublishRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.core.BitBlockBase
import me.wietlol.bitblock.core.BitSchemaBuilder
import me.wietlol.utils.common.ByteWrapper
import me.wietlol.utils.json.JacksonSerializerAdapter
import me.wietlol.utils.json.SimpleJsonSerializer
import me.wietlol.utils.json.lambda.LambdaException
import me.wietlol.utils.json.lambda.LambdaExceptionDeserializer
import me.wietlol.wietbot.data.commands.models.WietbotDataCommands
import me.wietlol.wietbot.data.commands.models.models.CommandCall
import me.wietlol.wietbot.data.commands.models.models.CommandCallImpl
import me.wietlol.wietbot.data.messages.models.models.ChatUserImpl
import me.wietlol.wietbot.data.messages.models.models.MessageSourceImpl
import org.junit.Test

class FakeSender
{
//	@Test
//	fun sendFakeMessage()
//	{
//		val commandCall: CommandCall = CommandCallImpl(
//			"evalNode",
//			"\"ohai\"",
//			MessageImpl(
//				12345,
//				ChatUserImpl(
//					12345,
//					"Wietlol",
//					"stack-overflow"
//				),
//				"@Wietbot evalNode \"ohai\"",
//				MessageSourceImpl(
//					1,
//					"Sandbox"
//				)
//			),
//			emptyMap(),
//		)
//
//		val snsClient: AmazonSNS = buildSnsClient()
//		val schema: Schema = buildBotCommandSchema()
//		val serializer: SimpleJsonSerializer = buildSimpleJsonSerializer()
//
//		snsClient.sendCommandToSns(commandCall, schema, serializer)
//	}
	
	private fun AmazonSNS.sendCommandToSns(commandCall: CommandCall, schema: Schema, serializer: SimpleJsonSerializer)
	{
		val json = commandCall
			.let { schema.serialize(it) }
			.let { ByteWrapper(it) }
			.let { serializer.serialize(it) }
		
		publish(PublishRequest("arn:aws:sns:eu-west-1:059598504952:wietbot-commands-evalNode", json))
	}
	
	private fun buildSnsClient(): AmazonSNS =
		AmazonSNSClientBuilder
			.standard()
			.withRegion(EU_WEST_1)
			.build()
	
	private fun buildSimpleJsonSerializer(): SimpleJsonSerializer =
		JacksonSerializerAdapter(buildJsonSerializer())
	
	private fun buildJsonSerializer(): ObjectMapper =
		ObjectMapper()
			.also { mapper ->
				mapper.registerModule(
					SimpleModule()
						.apply { addDeserializer(LambdaException::class.java, LambdaExceptionDeserializer(mapper)) }
				)
			}
	
	private fun buildBotCommandSchema(): Schema =
		BitSchemaBuilder.buildSchema(
			WietbotDataCommands::class.java.getResourceAsStream("/me/wietlol/wietbot/data/commands/models/Api.bitschema"),
			listOf(BitBlockBase, WietbotDataCommands),
		)
}
