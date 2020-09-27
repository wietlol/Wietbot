// hash: #8667ac1c
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
import me.wietlol.wietbot.data.commands.models.builders.RemoveCommandRequestBuilder
import me.wietlol.wietbot.data.commands.models.models.*
import me.wietlol.wietbot.data.commands.models.models.RemoveCommandRequest

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object RemoveCommandRequestSerializer : ModelSerializer<RemoveCommandRequest, RemoveCommandRequest>
{
	private val endOfObject: Int
		= 0
	
	private val keywordIndex: Int
		= 1
	
	override val modelId: UUID
		get() = RemoveCommandRequest.serializationKey
	
	override val dataClass: Class<RemoveCommandRequest>
		get() = RemoveCommandRequest::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: RemoveCommandRequest)
	{
		stream.writeUnsignedVarInt(keywordIndex)
		schema.serialize(serializationContext, stream, entity.keyword)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): RemoveCommandRequest
	{
		var keyword: String? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return RemoveCommandRequestImpl(
					keyword!!,
				)
				keywordIndex -> keyword = schema.deserialize(deserializationContext, stream)
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
