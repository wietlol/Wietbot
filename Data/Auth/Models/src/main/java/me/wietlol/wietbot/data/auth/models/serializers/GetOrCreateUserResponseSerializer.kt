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

object GetOrCreateUserResponseSerializer : ModelSerializer<GetOrCreateUserResponse, GetOrCreateUserResponse>
{
	private const val endOfObject = 0
	private const val userIndex = 1
	
	override val modelId: UUID
		get() = UUID.fromString("01b8cd9f-c37c-4d42-9fdc-b68cb9861e63")
	override val dataClass: Class<GetOrCreateUserResponse>
		get() = GetOrCreateUserResponse::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: GetOrCreateUserResponse)
	{
		stream.writeUnsignedVarInt(userIndex)
		schema.serialize(stream, entity.user)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): GetOrCreateUserResponse
	{
		val builder = GetOrCreateUserResponseBuilder()
		
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
