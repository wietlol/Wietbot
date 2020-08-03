package me.wietlol.wietbot.clients.stackexchange.botfeatures.commands

import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.model.PublishRequest
import me.wietlol.aws.lambda.LambdaRequest
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.serialization.SimpleJsonSerializer
import me.wietlol.wietbot.clients.stackexchange.botfeatures.CommandHandler
import me.wietlol.wietbot.data.commands.models.models.Command
import me.wietlol.wietbot.data.commands.models.models.CommandCall

data class ExternalCommand(
	val command: Command,
	val snsClient: AmazonSNS,
	val schema: Schema,
	val serializer: SimpleJsonSerializer
) : BotCommand
{
	override val keyword: String
		get() = command.keyword
	
	override fun execute(commandHandler: CommandHandler, commandCall: CommandCall)
	{
		sendCommandToSns(commandCall)
	}
	
	private fun sendCommandToSns(commandCall: CommandCall)
	{
		val json = commandCall
			.let { schema.serialize(it) }
			.let { LambdaRequest(it) }
			.let { serializer.serialize(it) }
		
		snsClient.publish(PublishRequest(command.listenerQueue, json))
	}
}
