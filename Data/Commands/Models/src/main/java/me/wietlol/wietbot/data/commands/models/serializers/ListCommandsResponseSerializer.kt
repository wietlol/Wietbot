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

object ListCommandsResponseSerializer : ModelSerializer<ListCommandsResponse, ListCommandsResponse>
{
	private const val endOfObject = 0
	private const val commandsIndex = 1
	
	override val modelId: UUID
		get() = UUID.fromString("0bab9d88-834a-4654-b151-28c11b8a2ea9")
	override val dataClass: Class<ListCommandsResponse>
		get() = ListCommandsResponse::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: ListCommandsResponse)
	{
		if (entity.commands.isNotEmpty())
		{
			stream.writeUnsignedVarInt(commandsIndex)
			entity.commands.forEach {
				schema.serialize(stream, it)
			}
			stream.writeUnsignedVarInt(endOfObject)
		}
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): ListCommandsResponse
	{
		val builder = ListCommandsResponseBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
				commandsIndex ->
				{
					while (true)
					{
						val key = stream.readUnsignedVarInt()
						if (key == 0)
							break
						
						builder.commands.add(schema.deserialize(stream, key))
					}
				}
			}
		}
	}
}
