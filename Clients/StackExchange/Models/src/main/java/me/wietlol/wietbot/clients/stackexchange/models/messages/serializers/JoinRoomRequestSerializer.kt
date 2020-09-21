// hash: #5a78de69
// @formatter:off
package me.wietlol.wietbot.clients.stackexchange.models.messages.serializers

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
import me.wietlol.wietbot.clients.stackexchange.models.messages.builders.JoinRoomRequestBuilder
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.*
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.JoinRoomRequest

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object JoinRoomRequestSerializer : ModelSerializer<JoinRoomRequest, JoinRoomRequest>
{
	private val endOfObject: Int
		= 0
	
	private val roomIdIndex: Int
		= 1
	
	override val modelId: UUID
		get() = JoinRoomRequest.serializationKey
	
	override val dataClass: Class<JoinRoomRequest>
		get() = JoinRoomRequest::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: JoinRoomRequest)
	{
		stream.writeUnsignedVarInt(roomIdIndex)
		schema.serialize(serializationContext, stream, entity.roomId)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): JoinRoomRequest
	{
		var roomId: Int? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return JoinRoomRequestImpl(
					roomId!!,
				)
				roomIdIndex -> roomId = schema.deserialize(deserializationContext, stream)
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
