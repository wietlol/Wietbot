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

object SendMessageRequestSerializer : ModelSerializer<SendMessageRequest, SendMessageRequest>
{
	private const val endOfObject = 0
	private const val roomIdIndex = 1
	private const val textIndex = 2
	
	override val modelId: UUID
		get() = UUID.fromString("eb4725da-5a53-4093-b1fd-a9f552732b97")
	override val dataClass: Class<SendMessageRequest>
		get() = SendMessageRequest::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: SendMessageRequest)
	{
		stream.writeUnsignedVarInt(roomIdIndex)
		schema.serialize(stream, entity.roomId)
		
		stream.writeUnsignedVarInt(textIndex)
		schema.serialize(stream, entity.text)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): SendMessageRequest
	{
		val builder = SendMessageRequestBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
				roomIdIndex -> builder.roomId = schema.deserialize(stream)
				textIndex -> builder.text = schema.deserialize(stream)
			}
		}
	}
}
