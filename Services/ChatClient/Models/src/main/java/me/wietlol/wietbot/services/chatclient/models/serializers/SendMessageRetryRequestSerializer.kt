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

object SendMessageRetryRequestSerializer : ModelSerializer<SendMessageRetryRequest, SendMessageRetryRequest>
{
	private const val endOfObject = 0
	private const val requestIndex = 1
	private const val tryCountIndex = 2
	
	override val modelId: UUID
		get() = UUID.fromString("1770e154-3d78-4ac9-9424-5750fcfa0262")
	override val dataClass: Class<SendMessageRetryRequest>
		get() = SendMessageRetryRequest::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: SendMessageRetryRequest)
	{
		stream.writeUnsignedVarInt(requestIndex)
		schema.serialize(stream, entity.request)
		
		stream.writeUnsignedVarInt(tryCountIndex)
		schema.serialize(stream, entity.tryCount)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): SendMessageRetryRequest
	{
		val builder = SendMessageRetryRequestBuilder()
		
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
