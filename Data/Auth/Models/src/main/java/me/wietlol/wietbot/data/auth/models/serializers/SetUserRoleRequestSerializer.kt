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

object SetUserRoleRequestSerializer : ModelSerializer<SetUserRoleRequest, SetUserRoleRequest>
{
	private const val endOfObject = 0
	private const val userIdIndex = 1
	private const val roleIndex = 2
	
	override val modelId: UUID
		get() = UUID.fromString("ce0896a2-4bae-42de-b3cd-89c7d94a2fd8")
	override val dataClass: Class<SetUserRoleRequest>
		get() = SetUserRoleRequest::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: SetUserRoleRequest)
	{
		stream.writeUnsignedVarInt(userIdIndex)
		schema.serialize(stream, entity.userId)
		
		stream.writeUnsignedVarInt(roleIndex)
		schema.serialize(stream, entity.role)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): SetUserRoleRequest
	{
		val builder = SetUserRoleRequestBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
				userIdIndex -> builder.userId = schema.deserialize(stream)
				roleIndex -> builder.role = schema.deserialize(stream)
			}
		}
	}
}
