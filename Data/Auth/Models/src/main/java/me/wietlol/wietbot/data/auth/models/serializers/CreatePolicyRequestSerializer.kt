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

object CreatePolicyRequestSerializer : ModelSerializer<CreatePolicyRequest, CreatePolicyRequest>
{
	private const val endOfObject = 0
	private const val nameIndex = 1
	
	override val modelId: UUID
		get() = UUID.fromString("bab87800-4fde-4894-820f-fec3f750139a")
	override val dataClass: Class<CreatePolicyRequest>
		get() = CreatePolicyRequest::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: CreatePolicyRequest)
	{
		stream.writeUnsignedVarInt(nameIndex)
		schema.serialize(stream, entity.name)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): CreatePolicyRequest
	{
		val builder = CreatePolicyRequestBuilder()
		
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
