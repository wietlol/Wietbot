package me.wietlol.wietbot.clients.stackexchange.client

import com.amazonaws.regions.Regions
import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import com.amazonaws.services.sqs.model.SendMessageRequest
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import me.wietlol.aws.lambda.LambdaRequest
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.core.BitBlock
import me.wietlol.bitblock.core.registry.LocalModelRegistry
import me.wietlol.bitblock.core.serialization.ImmutableSchema
import me.wietlol.serialization.JacksonSerializerAdapter
import me.wietlol.serialization.JsonSerializer2
import me.wietlol.wietbot.data.commands.models.WietbotDataCommands
import me.wietlol.wietbot.data.commands.models.models.CommandCall
import me.wietlol.wietbot.data.commands.models.models.Message
import me.wietlol.wietbot.data.commands.models.models.Room
import me.wietlol.wietbot.data.commands.models.models.User
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.BulkChatEvent
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.serializers.BulkChatEventDeserializer

object QueueTest
{
	@JvmStatic
	fun main(args: Array<String>)
	{
		val sqsClient: AmazonSQS = AmazonSQSClientBuilder.standard()
			.withRegion(Regions.EU_WEST_1)
			.build()
		
		val commandCall: CommandCall = CommandCall.of(
			"evalNode",
			"\"Hello from Dotnet\"",
			Message.of(
				1,
				User.of(
					1,
					"Wietlol"
				),
				"",
				Room.of(1)
			)
		)
		
		val schema: Schema = LocalModelRegistry()
			.apply(BitBlock::initialize)
			.apply(WietbotDataCommands::initialize)
			.let { ImmutableSchema(WietbotDataCommands::class.java.getResourceAsStream("/me/wietlol/wietbot/data/commands/models/Api.bitschema"), it) }
		
		val serializer: JsonSerializer2 = ObjectMapper()
			.also { it.registerModule(KotlinModule()) }
			.also { mapper ->
				mapper.registerModule(
					SimpleModule()
						.apply { addDeserializer(BulkChatEvent::class.java, BulkChatEventDeserializer(mapper)) }
				)
			}
			.let { JacksonSerializerAdapter(it) }
		
		val json = commandCall
			.let { schema.serialize(it) }
			.let { LambdaRequest(it) }
			.let { serializer.serialize(it) }
		
		val listenerQueue = "https://sqs.eu-west-1.amazonaws.com/059598504952/wietbot-commands-evalCSharp"
		
		sqsClient.sendMessage(SendMessageRequest().apply {
			messageBody = json
			queueUrl = listenerQueue
		})
	}
}
