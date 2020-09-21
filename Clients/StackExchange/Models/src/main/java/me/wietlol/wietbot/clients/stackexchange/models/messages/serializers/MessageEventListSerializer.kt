// hash: #dfdd6ce1
// @formatter:off
package me.wietlol.wietbot.clients.stackexchange.models.messages.serializers

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
import me.wietlol.wietbot.clients.stackexchange.models.messages.builders.MessageEventListBuilder
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.*
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.MessageEventList

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object MessageEventListSerializer : ModelSerializer<MessageEventList, MessageEventList>
{
	private val endOfObject: Int
		= 0
	
	private val messageIdIndex: Int
		= 1
	
	private val eventsIndex: Int
		= 2
	
	override val modelId: UUID
		get() = MessageEventList.serializationKey
	
	override val dataClass: Class<MessageEventList>
		get() = MessageEventList::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: MessageEventList)
	{
		stream.writeUnsignedVarInt(messageIdIndex)
		schema.serialize(serializationContext, stream, entity.messageId)
		
		stream.writeUnsignedVarInt(eventsIndex)
		schema.serialize(serializationContext, stream, entity.events)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): MessageEventList
	{
		var messageId: Int? = null
		var events: MutableList<MessageEvent>? = mutableListOf()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return MessageEventListImpl(
					messageId!!,
					events!!.toMutableList(),
				)
				messageIdIndex -> messageId = schema.deserialize(deserializationContext, stream)
				eventsIndex -> events = schema.deserialize(deserializationContext, stream)
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
