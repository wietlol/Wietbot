// hash: #461288ec
// @formatter:off
package me.wietlol.wietbot.data.commands.models.serializers

import java.io.InputStream
import java.io.OutputStream
import java.util.UUID
import me.wietlol.bitblock.api.serialization.DeserializationContext
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.SerializationContext
import me.wietlol.bitblock.api.serialization.deserialize
import me.wietlol.utils.common.streams.readUnsignedVarInt
import me.wietlol.utils.common.streams.writeUnsignedVarInt
import me.wietlol.wietbot.data.commands.models.builders.MessageBuilder
import me.wietlol.wietbot.data.commands.models.models.*
import me.wietlol.wietbot.data.commands.models.models.Message

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object MessageSerializer : ModelSerializer<Message, Message>
{
	private val endOfObject: Int
		= 0
	
	private val idIndex: Int
		= 1
	
	private val senderIndex: Int
		= 2
	
	private val fullTextIndex: Int
		= 3
	
	private val sourceIndex: Int
		= 4
	
	override val modelId: UUID
		get() = Message.serializationKey
	
	override val dataClass: Class<Message>
		get() = Message::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: Message)
	{
		stream.writeUnsignedVarInt(idIndex)
		schema.serialize(serializationContext, stream, entity.id)
		
		stream.writeUnsignedVarInt(senderIndex)
		schema.serialize(serializationContext, stream, entity.sender)
		
		stream.writeUnsignedVarInt(fullTextIndex)
		schema.serialize(serializationContext, stream, entity.fullText)
		
		stream.writeUnsignedVarInt(sourceIndex)
		schema.serialize(serializationContext, stream, entity.source)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): Message
	{
		var id: Int? = null
		var sender: ChatUser? = null
		var fullText: String? = null
		var source: MessageSource? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return MessageImpl(
					id!!,
					sender!!,
					fullText!!,
					source!!,
				)
				idIndex -> id = schema.deserialize(deserializationContext, stream)
				senderIndex -> sender = schema.deserialize(deserializationContext, stream)
				fullTextIndex -> fullText = schema.deserialize(deserializationContext, stream)
				sourceIndex -> source = schema.deserialize(deserializationContext, stream)
				else -> schema.deserialize<Any>(deserializationContext, stream)
			}
		}
	}
	
	// @formatter:on
	// @tomplot:customCode:start:5CFs54
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
