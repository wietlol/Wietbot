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

object CreatePermissionResponseSerializer : ModelSerializer<CreatePermissionResponse, CreatePermissionResponse>
{
	private const val endOfObject = 0
	
	override val modelId: UUID
		get() = UUID.fromString("64fbbb90-1d01-4d02-895d-444a689514cf")
	override val dataClass: Class<CreatePermissionResponse>
		get() = CreatePermissionResponse::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: CreatePermissionResponse)
	{
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): CreatePermissionResponse
	{
		val builder = CreatePermissionResponseBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
			}
		}
	}
}
