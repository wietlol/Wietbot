// hash: #03d083d5
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
import me.wietlol.wietbot.data.auth.models.builders.IsUserAuthorizedRequestBuilder
import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.IsUserAuthorizedRequest

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object IsUserAuthorizedRequestSerializer : ModelSerializer<IsUserAuthorizedRequest, IsUserAuthorizedRequest>
{
	private val endOfObject: Int
		= 0
	
	private val userIdIndex: Int
		= 1
	
	private val permissionIndex: Int
		= 2
	
	private val resourceIndex: Int
		= 3
	
	override val modelId: UUID
		get() = IsUserAuthorizedRequest.serializationKey
	
	override val dataClass: Class<IsUserAuthorizedRequest>
		get() = IsUserAuthorizedRequest::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: IsUserAuthorizedRequest)
	{
		stream.writeUnsignedVarInt(userIdIndex)
		schema.serialize(serializationContext, stream, entity.userId)
		
		stream.writeUnsignedVarInt(permissionIndex)
		schema.serialize(serializationContext, stream, entity.permission)
		
		stream.writeUnsignedVarInt(resourceIndex)
		schema.serialize(serializationContext, stream, entity.resource)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): IsUserAuthorizedRequest
	{
		var userId: Int? = null
		var permission: String? = null
		var resource: String? = "*"
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return IsUserAuthorizedRequestImpl(
					userId!!,
					permission!!,
					resource!!,
				)
				userIdIndex -> userId = schema.deserialize(deserializationContext, stream)
				permissionIndex -> permission = schema.deserialize(deserializationContext, stream)
				resourceIndex -> resource = schema.deserialize(deserializationContext, stream)
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
