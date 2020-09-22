// hash: #41802263
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
import me.wietlol.wietbot.data.auth.models.builders.CreatePermissionResponseBuilder
import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.CreatePermissionResponse

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object CreatePermissionResponseSerializer : ModelSerializer<CreatePermissionResponse, CreatePermissionResponse>
{
	private val endOfObject: Int
		= 0
	
	override val modelId: UUID
		get() = CreatePermissionResponse.serializationKey
	
	override val dataClass: Class<CreatePermissionResponse>
		get() = CreatePermissionResponse::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: CreatePermissionResponse)
	{
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): CreatePermissionResponse
	{
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return CreatePermissionResponseImpl(
				)
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
