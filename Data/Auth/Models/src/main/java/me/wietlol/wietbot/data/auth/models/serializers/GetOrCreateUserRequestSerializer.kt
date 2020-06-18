package me.wietlol.wietbot.data.auth.models.serializers

import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.deserialize
import me.wietlol.bitblock.core.BitBlock
import me.wietlol.bitblock.core.registry.CommonModelRegistryKey
import me.wietlol.common.readUnsignedVarInt
import me.wietlol.common.writeUnsignedVarInt
import java.io.InputStream
import java.io.OutputStream
import java.util.*

import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.builders.*

object GetOrCreateUserRequestSerializer : ModelSerializer<GetOrCreateUserRequest, GetOrCreateUserRequest>
{
	private const val endOfObject = 0
	private const val userIndex = 1
	
	override val modelId: UUID
		get() = UUID.fromString("c7b2d1e2-877a-4e42-938f-ea198b2c38f2")
	override val dataClass: Class<GetOrCreateUserRequest>
		get() = GetOrCreateUserRequest::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: GetOrCreateUserRequest)
	{
		stream.writeUnsignedVarInt(userIndex)
		schema.serialize(stream, entity.user)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): GetOrCreateUserRequest
	{
		val builder = GetOrCreateUserRequestBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
				userIndex -> builder.user = schema.deserialize(stream)
			}
		}
	}
}
