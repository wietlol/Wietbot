// hash: #25a930a0
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
import me.wietlol.wietbot.data.auth.models.builders.CreateRevokedAuthorityRequestBuilder
import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.CreateRevokedAuthorityRequest

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object CreateRevokedAuthorityRequestSerializer : ModelSerializer<CreateRevokedAuthorityRequest, CreateRevokedAuthorityRequest>
{
	private val endOfObject: Int
		= 0
	
	private val policyIndex: Int
		= 1
	
	private val permissionIndex: Int
		= 2
	
	private val resourceIndex: Int
		= 3
	
	override val modelId: UUID
		get() = CreateRevokedAuthorityRequest.serializationKey
	
	override val dataClass: Class<CreateRevokedAuthorityRequest>
		get() = CreateRevokedAuthorityRequest::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: CreateRevokedAuthorityRequest)
	{
		stream.writeUnsignedVarInt(policyIndex)
		schema.serialize(serializationContext, stream, entity.policy)
		
		stream.writeUnsignedVarInt(permissionIndex)
		schema.serialize(serializationContext, stream, entity.permission)
		
		stream.writeUnsignedVarInt(resourceIndex)
		schema.serialize(serializationContext, stream, entity.resource)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): CreateRevokedAuthorityRequest
	{
		var policy: String? = null
		var permission: String? = null
		var resource: String? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return DefaultCreateRevokedAuthorityRequest(
					policy!!,
					permission!!,
					resource!!,
				)
				policyIndex -> policy = schema.deserialize(deserializationContext, stream)
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
