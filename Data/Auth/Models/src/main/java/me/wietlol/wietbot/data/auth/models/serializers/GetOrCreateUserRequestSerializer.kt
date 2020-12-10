// hash: #7083863a
// @formatter:off
package me.wietlol.wietbot.data.auth.models.serializers

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
import me.wietlol.wietbot.data.auth.models.builders.GetOrCreateUserRequestBuilder
import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.GetOrCreateUserRequest

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object GetOrCreateUserRequestSerializer : ModelSerializer<GetOrCreateUserRequest, GetOrCreateUserRequest>
{
	private val endOfObject: Int
		= 0
	
	private val localIdIndex: Int
		= 1
	
	private val localNameIndex: Int
		= 2
	
	private val platformIndex: Int
		= 3
	
	override val modelId: UUID
		get() = GetOrCreateUserRequest.serializationKey
	
	override val dataClass: Class<GetOrCreateUserRequest>
		get() = GetOrCreateUserRequest::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: GetOrCreateUserRequest)
	{
		stream.writeUnsignedVarInt(localIdIndex)
		schema.serialize(serializationContext, stream, entity.localId)
		
		stream.writeUnsignedVarInt(localNameIndex)
		schema.serialize(serializationContext, stream, entity.localName)
		
		stream.writeUnsignedVarInt(platformIndex)
		schema.serialize(serializationContext, stream, entity.platform)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): GetOrCreateUserRequest
	{
		var localId: String? = null
		var localName: String? = null
		var platform: Platform? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return GetOrCreateUserRequestImpl(
					localId!!,
					localName!!,
					platform!!,
				)
				localIdIndex -> localId = schema.deserialize(deserializationContext, stream)
				localNameIndex -> localName = schema.deserialize(deserializationContext, stream)
				platformIndex -> platform = schema.deserialize(deserializationContext, stream)
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
