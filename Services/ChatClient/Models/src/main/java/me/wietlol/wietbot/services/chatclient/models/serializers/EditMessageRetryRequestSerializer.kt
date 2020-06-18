package me.wietlol.wietbot.services.chatclient.models.serializers

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

import me.wietlol.wietbot.services.chatclient.models.models.*
import me.wietlol.wietbot.services.chatclient.models.builders.*

object EditMessageRetryRequestSerializer : ModelSerializer<EditMessageRetryRequest, EditMessageRetryRequest>
{
	private const val endOfObject = 0
	private const val requestIndex = 1
	private const val tryCountIndex = 2
	
	override val modelId: UUID
		get() = UUID.fromString("404c5d83-7b07-49dc-ad86-3a3b07461de9")
	override val dataClass: Class<EditMessageRetryRequest>
		get() = EditMessageRetryRequest::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: EditMessageRetryRequest)
	{
		stream.writeUnsignedVarInt(requestIndex)
		schema.serialize(stream, entity.request)
		
		stream.writeUnsignedVarInt(tryCountIndex)
		schema.serialize(stream, entity.tryCount)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): EditMessageRetryRequest
	{
		val builder = EditMessageRetryRequestBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
				requestIndex -> builder.request = schema.deserialize(stream)
				tryCountIndex -> builder.tryCount = schema.deserialize(stream)
			}
		}
	}
}
