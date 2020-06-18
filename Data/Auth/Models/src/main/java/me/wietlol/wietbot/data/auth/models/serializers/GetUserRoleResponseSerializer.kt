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

object GetUserRoleResponseSerializer : ModelSerializer<GetUserRoleResponse, GetUserRoleResponse>
{
	private const val endOfObject = 0
	private const val roleIndex = 1
	
	override val modelId: UUID
		get() = UUID.fromString("c8e9ab2b-3cf6-4c83-b15e-c17b49c1947e")
	override val dataClass: Class<GetUserRoleResponse>
		get() = GetUserRoleResponse::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: GetUserRoleResponse)
	{
		stream.writeUnsignedVarInt(roleIndex)
		schema.serialize(stream, entity.role)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): GetUserRoleResponse
	{
		val builder = GetUserRoleResponseBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
				roleIndex -> builder.role = schema.deserialize(stream)
			}
		}
	}
}
