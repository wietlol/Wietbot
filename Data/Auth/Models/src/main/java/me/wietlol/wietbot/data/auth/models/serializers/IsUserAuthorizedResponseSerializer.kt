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

object IsUserAuthorizedResponseSerializer : ModelSerializer<IsUserAuthorizedResponse, IsUserAuthorizedResponse>
{
	private const val endOfObject = 0
	private const val isAuthorizedIndex = 1
	
	override val modelId: UUID
		get() = UUID.fromString("b706278e-18ef-4fe9-b7d4-ac6f8e387a85")
	override val dataClass: Class<IsUserAuthorizedResponse>
		get() = IsUserAuthorizedResponse::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: IsUserAuthorizedResponse)
	{
		stream.writeUnsignedVarInt(isAuthorizedIndex)
		schema.serialize(stream, entity.isAuthorized)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): IsUserAuthorizedResponse
	{
		val builder = IsUserAuthorizedResponseBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
				isAuthorizedIndex -> builder.isAuthorized = schema.deserialize(stream)
			}
		}
	}
}
