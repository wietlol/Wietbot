package me.wietlol.wietbot.features.commandhandler.core.models

import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.model.PublishRequest
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.utils.common.ByteWrapper
import me.wietlol.utils.json.SimpleJsonSerializer
import me.wietlol.wietbot.data.commands.models.models.Command
import me.wietlol.wietbot.data.commands.models.models.CommandCall

class BotCommand(
	val command: Command,
	val snsClient: AmazonSNS,
	val schema: Schema,
	val serializer: SimpleJsonSerializer
)
{
	val keyword: String
		get() = command.keyword
	
	fun execute(commandCall: CommandCall)
	{
		sendCommandToSns(commandCall)
	}
	
	private fun sendCommandToSns(commandCall: CommandCall)
	{
		val json = commandCall
			.let { schema.serialize(it) }
			.let { ByteWrapper(it) }
			.let { serializer.serialize(it) }
		
		snsClient.publish(PublishRequest(command.listenerQueue, json))
	}
}
