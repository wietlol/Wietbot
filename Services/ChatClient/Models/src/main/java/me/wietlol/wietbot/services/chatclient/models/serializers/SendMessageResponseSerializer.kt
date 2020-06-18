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

object SendMessageResponseSerializer : ModelSerializer<SendMessageResponse, SendMessageResponse>
{
	private const val endOfObject = 0
	private const val idIndex = 1
	
	override val modelId: UUID
		get() = UUID.fromString("ed5692e7-4455-45be-a93e-2c11e69cb996")
	override val dataClass: Class<SendMessageResponse>
		get() = SendMessageResponse::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: SendMessageResponse)
	{
		stream.writeUnsignedVarInt(idIndex)
		schema.serialize(stream, entity.id)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): SendMessageResponse
	{
		val builder = SendMessageResponseBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
				idIndex -> builder.id = schema.deserialize(stream)
			}
		}
	}
}
