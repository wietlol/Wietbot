// hash: #7f80e502
// @formatter:off
package me.wietlol.wietbot.data.commands.models.serializers

import java.io.InputStream
import java.io.OutputStream
import java.util.UUID
import me.wietlol.bitblock.api.serialization.DeserializationContext
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.SerializationContext
import me.wietlol.bitblock.api.serialization.deserialize
import me.wietlol.utils.common.streams.readUnsignedVarInt
import me.wietlol.utils.common.streams.writeUnsignedVarInt
import me.wietlol.wietbot.data.commands.models.builders.CommandCallBuilder
import me.wietlol.wietbot.data.commands.models.models.*
import me.wietlol.wietbot.data.commands.models.models.CommandCall

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object CommandCallSerializer : ModelSerializer<CommandCall, CommandCall>
{
	private val endOfObject: Int
		= 0
	
	private val commandKeywordIndex: Int
		= 1
	
	private val argumentTextIndex: Int
		= 2
	
	private val messageIndex: Int
		= 3
	
	private val optionsIndex: Int
		= 4
	
	override val modelId: UUID
		get() = CommandCall.serializationKey
	
	override val dataClass: Class<CommandCall>
		get() = CommandCall::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: CommandCall)
	{
		stream.writeUnsignedVarInt(commandKeywordIndex)
		schema.serialize(serializationContext, stream, entity.commandKeyword)
		
		stream.writeUnsignedVarInt(argumentTextIndex)
		schema.serialize(serializationContext, stream, entity.argumentText)
		
		stream.writeUnsignedVarInt(messageIndex)
		schema.serialize(serializationContext, stream, entity.message)
		
		stream.writeUnsignedVarInt(optionsIndex)
		schema.serialize(serializationContext, stream, entity.options)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): CommandCall
	{
		var commandKeyword: String? = null
		var argumentText: String? = null
		var message: Message? = null
		var options: MutableMap<String, String>? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return CommandCallImpl(
					commandKeyword!!,
					argumentText!!,
					message!!,
					options!!,
				)
				commandKeywordIndex -> commandKeyword = schema.deserialize(deserializationContext, stream)
				argumentTextIndex -> argumentText = schema.deserialize(deserializationContext, stream)
				messageIndex -> message = schema.deserialize(deserializationContext, stream)
				optionsIndex -> options = schema.deserialize(deserializationContext, stream)
				else -> schema.deserialize<Any>(deserializationContext, stream)
			}
		}
	}
	
	// @formatter:on
	// @tomplot:customCode:start:5CFs54
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
