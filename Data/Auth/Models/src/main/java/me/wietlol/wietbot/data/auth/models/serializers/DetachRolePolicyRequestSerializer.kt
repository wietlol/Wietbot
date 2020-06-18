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

object DetachRolePolicyRequestSerializer : ModelSerializer<DetachRolePolicyRequest, DetachRolePolicyRequest>
{
	private const val endOfObject = 0
	private const val roleIndex = 1
	private const val policyIndex = 2
	
	override val modelId: UUID
		get() = UUID.fromString("c75fc1a3-c3e7-467c-aeca-1474f25cf8b3")
	override val dataClass: Class<DetachRolePolicyRequest>
		get() = DetachRolePolicyRequest::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: DetachRolePolicyRequest)
	{
		stream.writeUnsignedVarInt(roleIndex)
		schema.serialize(stream, entity.role)
		
		stream.writeUnsignedVarInt(policyIndex)
		schema.serialize(stream, entity.policy)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): DetachRolePolicyRequest
	{
		val builder = DetachRolePolicyRequestBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
				roleIndex -> builder.role = schema.deserialize(stream)
				policyIndex -> builder.policy = schema.deserialize(stream)
			}
		}
	}
}
