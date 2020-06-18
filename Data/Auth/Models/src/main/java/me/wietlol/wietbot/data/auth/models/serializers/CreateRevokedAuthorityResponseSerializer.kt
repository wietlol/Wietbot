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

object CreateRevokedAuthorityResponseSerializer : ModelSerializer<CreateRevokedAuthorityResponse, CreateRevokedAuthorityResponse>
{
	private const val endOfObject = 0
	
	override val modelId: UUID
		get() = UUID.fromString("644d1a37-717f-41d6-bb7e-6a3c5e5a875c")
	override val dataClass: Class<CreateRevokedAuthorityResponse>
		get() = CreateRevokedAuthorityResponse::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: CreateRevokedAuthorityResponse)
	{
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): CreateRevokedAuthorityResponse
	{
		val builder = CreateRevokedAuthorityResponseBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
			}
		}
	}
}
