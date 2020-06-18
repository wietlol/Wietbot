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

object EditMessageRequestSerializer : ModelSerializer<EditMessageRequest, EditMessageRequest>
{
	private const val endOfObject = 0
	private const val messageIdIndex = 1
	private const val textIndex = 2
	
	override val modelId: UUID
		get() = UUID.fromString("6e027d37-2a4b-47c5-99fc-8875db239e0b")
	override val dataClass: Class<EditMessageRequest>
		get() = EditMessageRequest::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: EditMessageRequest)
	{
		stream.writeUnsignedVarInt(messageIdIndex)
		schema.serialize(stream, entity.messageId)
		
		stream.writeUnsignedVarInt(textIndex)
		schema.serialize(stream, entity.text)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): EditMessageRequest
	{
		val builder = EditMessageRequestBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
				messageIdIndex -> builder.messageId = schema.deserialize(stream)
				textIndex -> builder.text = schema.deserialize(stream)
			}
		}
	}
}
