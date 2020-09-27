// hash: #216b3c5d
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
import me.wietlol.wietbot.data.commands.models.builders.ListCommandsResponseBuilder
import me.wietlol.wietbot.data.commands.models.models.*
import me.wietlol.wietbot.data.commands.models.models.ListCommandsResponse

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object ListCommandsResponseSerializer : ModelSerializer<ListCommandsResponse, ListCommandsResponse>
{
	private val endOfObject: Int
		= 0
	
	private val commandsIndex: Int
		= 1
	
	override val modelId: UUID
		get() = ListCommandsResponse.serializationKey
	
	override val dataClass: Class<ListCommandsResponse>
		get() = ListCommandsResponse::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: ListCommandsResponse)
	{
		stream.writeUnsignedVarInt(commandsIndex)
		schema.serialize(serializationContext, stream, entity.commands)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): ListCommandsResponse
	{
		var commands: MutableList<Command>? = mutableListOf()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return ListCommandsResponseImpl(
					commands!!.toMutableList(),
				)
				commandsIndex -> commands = schema.deserialize(deserializationContext, stream)
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
