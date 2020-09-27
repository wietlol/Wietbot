// hash: #806c0a59
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
import me.wietlol.wietbot.data.commands.models.builders.CommandBuilder
import me.wietlol.wietbot.data.commands.models.models.*
import me.wietlol.wietbot.data.commands.models.models.Command

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object CommandSerializer : ModelSerializer<Command, Command>
{
	private val endOfObject: Int
		= 0
	
	private val keywordIndex: Int
		= 1
	
	private val listenerQueueIndex: Int
		= 2
	
	override val modelId: UUID
		get() = Command.serializationKey
	
	override val dataClass: Class<Command>
		get() = Command::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: Command)
	{
		stream.writeUnsignedVarInt(keywordIndex)
		schema.serialize(serializationContext, stream, entity.keyword)
		
		stream.writeUnsignedVarInt(listenerQueueIndex)
		schema.serialize(serializationContext, stream, entity.listenerQueue)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): Command
	{
		var keyword: String? = null
		var listenerQueue: String? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return CommandImpl(
					keyword!!,
					listenerQueue!!,
				)
				keywordIndex -> keyword = schema.deserialize(deserializationContext, stream)
				listenerQueueIndex -> listenerQueue = schema.deserialize(deserializationContext, stream)
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
