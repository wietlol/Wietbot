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

object GetUserRoleRequestSerializer : ModelSerializer<GetUserRoleRequest, GetUserRoleRequest>
{
	private const val endOfObject = 0
	private const val userIdIndex = 1
	
	override val modelId: UUID
		get() = UUID.fromString("afe9f6d5-e3ec-4f09-8294-cabeb5a8c279")
	override val dataClass: Class<GetUserRoleRequest>
		get() = GetUserRoleRequest::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: GetUserRoleRequest)
	{
		stream.writeUnsignedVarInt(userIdIndex)
		schema.serialize(stream, entity.userId)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): GetUserRoleRequest
	{
		val builder = GetUserRoleRequestBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
				userIdIndex -> builder.userId = schema.deserialize(stream)
			}
		}
	}
}
