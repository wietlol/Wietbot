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

object CommandSerializer : ModelSerializer<Command, Command>
{
	private const val endOfObject = 0
	private const val keywordIndex = 1
	private const val listenerQueueIndex = 2
	
	override val modelId: UUID
		get() = UUID.fromString("9223167e-cac3-4ce2-8293-7aeb2b85a8b6")
	override val dataClass: Class<Command>
		get() = Command::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: Command)
	{
		stream.writeUnsignedVarInt(keywordIndex)
		schema.serialize(stream, entity.keyword)
		
		stream.writeUnsignedVarInt(listenerQueueIndex)
		schema.serialize(stream, entity.listenerQueue)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): Command
	{
		val builder = CommandBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
				keywordIndex -> builder.keyword = schema.deserialize(stream)
				listenerQueueIndex -> builder.listenerQueue = schema.deserialize(stream)
			}
		}
	}
}
