package me.wietlol.wietbot.data.commands.models.serializers

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

import me.wietlol.wietbot.data.commands.models.models.*
import me.wietlol.wietbot.data.commands.models.builders.*

object CreateCommandRequestSerializer : ModelSerializer<CreateCommandRequest, CreateCommandRequest>
{
	private const val endOfObject = 0
	private const val keywordIndex = 1
	private const val listenerQueueIndex = 2
	
	override val modelId: UUID
		get() = UUID.fromString("abd0411b-be58-4dc2-8319-10f4f16dd2b7")
	override val dataClass: Class<CreateCommandRequest>
		get() = CreateCommandRequest::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: CreateCommandRequest)
	{
		stream.writeUnsignedVarInt(keywordIndex)
		schema.serialize(stream, entity.keyword)
		
		stream.writeUnsignedVarInt(listenerQueueIndex)
		schema.serialize(stream, entity.listenerQueue)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): CreateCommandRequest
	{
		val builder = CreateCommandRequestBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
				keywordIndex -> builder.keyword = schema.deserialize(stream)
				listenerQueueIndex -> builder.listenerQueue = schema.deserialize(stream)
			}
		}
	}
}
