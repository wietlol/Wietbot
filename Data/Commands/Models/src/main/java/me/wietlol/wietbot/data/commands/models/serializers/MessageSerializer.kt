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

object MessageSerializer : ModelSerializer<Message, Message>
{
	private const val endOfObject = 0
	private const val idIndex = 1
	private const val senderIndex = 2
	private const val fullTextIndex = 3
	private const val sourceIndex = 4
	
	override val modelId: UUID
		get() = UUID.fromString("68f7784b-2fee-489d-af59-f29fa4e3b405")
	override val dataClass: Class<Message>
		get() = Message::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: Message)
	{
		stream.writeUnsignedVarInt(idIndex)
		schema.serialize(stream, entity.id)
		
		stream.writeUnsignedVarInt(senderIndex)
		schema.serialize(stream, entity.sender)
		
		stream.writeUnsignedVarInt(fullTextIndex)
		schema.serialize(stream, entity.fullText)
		
		stream.writeUnsignedVarInt(sourceIndex)
		schema.serialize(stream, entity.source)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): Message
	{
		val builder = MessageBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
				idIndex -> builder.id = schema.deserialize(stream)
				senderIndex -> builder.sender = schema.deserialize(stream)
				fullTextIndex -> builder.fullText = schema.deserialize(stream)
				sourceIndex -> builder.source = schema.deserialize(stream)
			}
		}
	}
}
