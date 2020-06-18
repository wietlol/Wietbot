package me.wietlol.wietbot.data.commands.models.serializers

import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.deserialize
import me.wietlol.bitblock.core.BitBlock
import me.wietlol.bitblock.core.registry.CommonModelRegistryKey
import me.wietlol.common.readUnsignedVarInt
import me.wietlol.common.writeUnsignedVarInt
import java.io.InputStream
import java.io.OutputStream
import java.util.*

import me.wietlol.wietbot.data.commands.models.models.*
import me.wietlol.wietbot.data.commands.models.builders.*

object CommandCallSerializer : ModelSerializer<CommandCall, CommandCall>
{
	private const val endOfObject = 0
	private const val commandKeywordIndex = 1
	private const val argumentTextIndex = 2
	private const val messageIndex = 3
	
	override val modelId: UUID
		get() = UUID.fromString("9f435486-805e-4d00-a7c2-b4eec08750b3")
	override val dataClass: Class<CommandCall>
		get() = CommandCall::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: CommandCall)
	{
		stream.writeUnsignedVarInt(commandKeywordIndex)
		schema.serialize(stream, entity.commandKeyword)
		
		stream.writeUnsignedVarInt(argumentTextIndex)
		schema.serialize(stream, entity.argumentText)
		
		stream.writeUnsignedVarInt(messageIndex)
		schema.serialize(stream, entity.message)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): CommandCall
	{
		val builder = CommandCallBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
				commandKeywordIndex -> builder.commandKeyword = schema.deserialize(stream)
				argumentTextIndex -> builder.argumentText = schema.deserialize(stream)
				messageIndex -> builder.message = schema.deserialize(stream)
			}
		}
	}
}
