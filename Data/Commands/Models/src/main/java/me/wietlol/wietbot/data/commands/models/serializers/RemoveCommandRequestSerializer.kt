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

object RemoveCommandRequestSerializer : ModelSerializer<RemoveCommandRequest, RemoveCommandRequest>
{
	private const val endOfObject = 0
	private const val keywordIndex = 1
	
	override val modelId: UUID
		get() = UUID.fromString("5351488e-42c9-4a5c-a084-d54fbaab660f")
	override val dataClass: Class<RemoveCommandRequest>
		get() = RemoveCommandRequest::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: RemoveCommandRequest)
	{
		stream.writeUnsignedVarInt(keywordIndex)
		schema.serialize(stream, entity.keyword)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): RemoveCommandRequest
	{
		val builder = RemoveCommandRequestBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
				keywordIndex -> builder.keyword = schema.deserialize(stream)
			}
		}
	}
}
