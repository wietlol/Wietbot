// hash: #1b4e46fb
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
import me.wietlol.wietbot.data.auth.models.builders.IsUserAuthorizedResponseBuilder
import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.IsUserAuthorizedResponse

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object IsUserAuthorizedResponseSerializer : ModelSerializer<IsUserAuthorizedResponse, IsUserAuthorizedResponse>
{
	private val endOfObject: Int
		= 0
	
	private val isAuthorizedIndex: Int
		= 1
	
	override val modelId: UUID
		get() = IsUserAuthorizedResponse.serializationKey
	
	override val dataClass: Class<IsUserAuthorizedResponse>
		get() = IsUserAuthorizedResponse::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: IsUserAuthorizedResponse)
	{
		stream.writeUnsignedVarInt(isAuthorizedIndex)
		schema.serialize(serializationContext, stream, entity.isAuthorized)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): IsUserAuthorizedResponse
	{
		var isAuthorized: Boolean? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return DefaultIsUserAuthorizedResponse(
					isAuthorized!!,
				)
				isAuthorizedIndex -> isAuthorized = schema.deserialize(deserializationContext, stream)
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
