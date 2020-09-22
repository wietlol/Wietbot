// hash: #4e04b9f8
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
import me.wietlol.wietbot.data.auth.models.builders.SetUserRoleRequestBuilder
import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.SetUserRoleRequest

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object SetUserRoleRequestSerializer : ModelSerializer<SetUserRoleRequest, SetUserRoleRequest>
{
	private val endOfObject: Int
		= 0
	
	private val userIdIndex: Int
		= 1
	
	private val roleIndex: Int
		= 2
	
	override val modelId: UUID
		get() = SetUserRoleRequest.serializationKey
	
	override val dataClass: Class<SetUserRoleRequest>
		get() = SetUserRoleRequest::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: SetUserRoleRequest)
	{
		stream.writeUnsignedVarInt(userIdIndex)
		schema.serialize(serializationContext, stream, entity.userId)
		
		stream.writeUnsignedVarInt(roleIndex)
		schema.serialize(serializationContext, stream, entity.role)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): SetUserRoleRequest
	{
		var userId: Int? = null
		var role: String? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return SetUserRoleRequestImpl(
					userId!!,
					role!!,
				)
				userIdIndex -> userId = schema.deserialize(deserializationContext, stream)
				roleIndex -> role = schema.deserialize(deserializationContext, stream)
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
