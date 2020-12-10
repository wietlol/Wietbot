// hash: #40a584be
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
import me.wietlol.wietbot.data.auth.models.builders.GetUserRoleRequestBuilder
import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.GetUserRoleRequest

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object GetUserRoleRequestSerializer : ModelSerializer<GetUserRoleRequest, GetUserRoleRequest>
{
	private val endOfObject: Int
		= 0
	
	private val localUserIdIndex: Int
		= 1
	
	private val platformIndex: Int
		= 2
	
	override val modelId: UUID
		get() = GetUserRoleRequest.serializationKey
	
	override val dataClass: Class<GetUserRoleRequest>
		get() = GetUserRoleRequest::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: GetUserRoleRequest)
	{
		stream.writeUnsignedVarInt(localUserIdIndex)
		schema.serialize(serializationContext, stream, entity.localUserId)
		
		stream.writeUnsignedVarInt(platformIndex)
		schema.serialize(serializationContext, stream, entity.platform)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): GetUserRoleRequest
	{
		var localUserId: String? = null
		var platform: Platform? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return GetUserRoleRequestImpl(
					localUserId!!,
					platform!!,
				)
				localUserIdIndex -> localUserId = schema.deserialize(deserializationContext, stream)
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
