// hash: #2298b224
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
import me.wietlol.wietbot.data.auth.models.builders.GetOrCreateUserResponseBuilder
import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.GetOrCreateUserResponse

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object GetOrCreateUserResponseSerializer : ModelSerializer<GetOrCreateUserResponse, GetOrCreateUserResponse>
{
	private val endOfObject: Int
		= 0
	
	private val userIndex: Int
		= 1
	
	override val modelId: UUID
		get() = GetOrCreateUserResponse.serializationKey
	
	override val dataClass: Class<GetOrCreateUserResponse>
		get() = GetOrCreateUserResponse::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: GetOrCreateUserResponse)
	{
		stream.writeUnsignedVarInt(userIndex)
		schema.serialize(serializationContext, stream, entity.user)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): GetOrCreateUserResponse
	{
		var user: User? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return DefaultGetOrCreateUserResponse(
					user!!,
				)
				userIndex -> user = schema.deserialize(deserializationContext, stream)
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
