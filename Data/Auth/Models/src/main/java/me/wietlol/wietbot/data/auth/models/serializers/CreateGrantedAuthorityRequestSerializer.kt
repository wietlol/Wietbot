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

object CreateGrantedAuthorityRequestSerializer : ModelSerializer<CreateGrantedAuthorityRequest, CreateGrantedAuthorityRequest>
{
	private const val endOfObject = 0
	private const val policyIndex = 1
	private const val permissionIndex = 2
	private const val resourceIndex = 3
	
	override val modelId: UUID
		get() = UUID.fromString("12480305-5d03-47b7-aaea-54961501371b")
	override val dataClass: Class<CreateGrantedAuthorityRequest>
		get() = CreateGrantedAuthorityRequest::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: CreateGrantedAuthorityRequest)
	{
		stream.writeUnsignedVarInt(policyIndex)
		schema.serialize(stream, entity.policy)
		
		stream.writeUnsignedVarInt(permissionIndex)
		schema.serialize(stream, entity.permission)
		
		stream.writeUnsignedVarInt(resourceIndex)
		schema.serialize(stream, entity.resource)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): CreateGrantedAuthorityRequest
	{
		val builder = CreateGrantedAuthorityRequestBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
				policyIndex -> builder.policy = schema.deserialize(stream)
				permissionIndex -> builder.permission = schema.deserialize(stream)
				resourceIndex -> builder.resource = schema.deserialize(stream)
			}
		}
	}
}
