// hash: #7f494ed2
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
import me.wietlol.wietbot.data.auth.models.builders.AttachRolePolicyRequestBuilder
import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.AttachRolePolicyRequest

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object AttachRolePolicyRequestSerializer : ModelSerializer<AttachRolePolicyRequest, AttachRolePolicyRequest>
{
	private val endOfObject: Int
		= 0
	
	private val roleIndex: Int
		= 1
	
	private val policyIndex: Int
		= 2
	
	override val modelId: UUID
		get() = AttachRolePolicyRequest.serializationKey
	
	override val dataClass: Class<AttachRolePolicyRequest>
		get() = AttachRolePolicyRequest::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: AttachRolePolicyRequest)
	{
		stream.writeUnsignedVarInt(roleIndex)
		schema.serialize(serializationContext, stream, entity.role)
		
		stream.writeUnsignedVarInt(policyIndex)
		schema.serialize(serializationContext, stream, entity.policy)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): AttachRolePolicyRequest
	{
		var role: String? = null
		var policy: String? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return AttachRolePolicyRequestImpl(
					role!!,
					policy!!,
				)
				roleIndex -> role = schema.deserialize(deserializationContext, stream)
				policyIndex -> policy = schema.deserialize(deserializationContext, stream)
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
