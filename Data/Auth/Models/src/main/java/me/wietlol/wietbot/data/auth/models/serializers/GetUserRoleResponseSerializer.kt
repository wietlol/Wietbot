// hash: #12f2cbab
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
import me.wietlol.wietbot.data.auth.models.builders.GetUserRoleResponseBuilder
import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.GetUserRoleResponse

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object GetUserRoleResponseSerializer : ModelSerializer<GetUserRoleResponse, GetUserRoleResponse>
{
	private val endOfObject: Int
		= 0
	
	private val roleIndex: Int
		= 1
	
	override val modelId: UUID
		get() = GetUserRoleResponse.serializationKey
	
	override val dataClass: Class<GetUserRoleResponse>
		get() = GetUserRoleResponse::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: GetUserRoleResponse)
	{
		stream.writeUnsignedVarInt(roleIndex)
		schema.serialize(serializationContext, stream, entity.role)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): GetUserRoleResponse
	{
		var role: Role? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return DefaultGetUserRoleResponse(
					role!!,
				)
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
