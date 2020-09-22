// hash: #fd922550
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
import me.wietlol.wietbot.data.auth.models.builders.CreateGrantedAuthorityRequestBuilder
import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.CreateGrantedAuthorityRequest

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object CreateGrantedAuthorityRequestSerializer : ModelSerializer<CreateGrantedAuthorityRequest, CreateGrantedAuthorityRequest>
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
		get() = CreateGrantedAuthorityRequest.serializationKey
	
	override val dataClass: Class<CreateGrantedAuthorityRequest>
		get() = CreateGrantedAuthorityRequest::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: CreateGrantedAuthorityRequest)
	{
		stream.writeUnsignedVarInt(policyIndex)
		schema.serialize(serializationContext, stream, entity.policy)
		
		stream.writeUnsignedVarInt(permissionIndex)
		schema.serialize(serializationContext, stream, entity.permission)
		
		stream.writeUnsignedVarInt(resourceIndex)
		schema.serialize(serializationContext, stream, entity.resource)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): CreateGrantedAuthorityRequest
	{
		var policy: String? = null
		var permission: String? = null
		var resource: String? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return CreateGrantedAuthorityRequestImpl(
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
