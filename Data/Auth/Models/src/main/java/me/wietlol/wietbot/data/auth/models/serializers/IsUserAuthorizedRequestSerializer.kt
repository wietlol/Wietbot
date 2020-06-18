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

object IsUserAuthorizedRequestSerializer : ModelSerializer<IsUserAuthorizedRequest, IsUserAuthorizedRequest>
{
	private const val endOfObject = 0
	private const val userIdIndex = 1
	private const val permissionIndex = 2
	private const val resourceIndex = 3
	
	override val modelId: UUID
		get() = UUID.fromString("b97ca999-b513-4a5c-a0a1-14979af3df94")
	override val dataClass: Class<IsUserAuthorizedRequest>
		get() = IsUserAuthorizedRequest::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: IsUserAuthorizedRequest)
	{
		stream.writeUnsignedVarInt(userIdIndex)
		schema.serialize(stream, entity.userId)
		
		stream.writeUnsignedVarInt(permissionIndex)
		schema.serialize(stream, entity.permission)
		
		stream.writeUnsignedVarInt(resourceIndex)
		schema.serialize(stream, entity.resource)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): IsUserAuthorizedRequest
	{
		val builder = IsUserAuthorizedRequestBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
				userIdIndex -> builder.userId = schema.deserialize(stream)
				permissionIndex -> builder.permission = schema.deserialize(stream)
				resourceIndex -> builder.resource = schema.deserialize(stream)
			}
		}
	}
}
