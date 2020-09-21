// hash: #7c9eda7a
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
import me.wietlol.wietbot.clients.stackexchange.models.messages.builders.LeaveRoomRequestBuilder
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.*
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.LeaveRoomRequest

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object LeaveRoomRequestSerializer : ModelSerializer<LeaveRoomRequest, LeaveRoomRequest>
{
	private val endOfObject: Int
		= 0
	
	private val roomIdIndex: Int
		= 1
	
	override val modelId: UUID
		get() = LeaveRoomRequest.serializationKey
	
	override val dataClass: Class<LeaveRoomRequest>
		get() = LeaveRoomRequest::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: LeaveRoomRequest)
	{
		stream.writeUnsignedVarInt(roomIdIndex)
		schema.serialize(serializationContext, stream, entity.roomId)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): LeaveRoomRequest
	{
		var roomId: Int? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return LeaveRoomRequestImpl(
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
