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

object CreatePermissionRequestSerializer : ModelSerializer<CreatePermissionRequest, CreatePermissionRequest>
{
	private const val endOfObject = 0
	private const val nameIndex = 1
	
	override val modelId: UUID
		get() = UUID.fromString("4d213b21-3f49-4751-85ca-b1f1b3cd3eb8")
	override val dataClass: Class<CreatePermissionRequest>
		get() = CreatePermissionRequest::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: CreatePermissionRequest)
	{
		stream.writeUnsignedVarInt(nameIndex)
		schema.serialize(stream, entity.name)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): CreatePermissionRequest
	{
		val builder = CreatePermissionRequestBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
				nameIndex -> builder.name = schema.deserialize(stream)
			}
		}
	}
}
