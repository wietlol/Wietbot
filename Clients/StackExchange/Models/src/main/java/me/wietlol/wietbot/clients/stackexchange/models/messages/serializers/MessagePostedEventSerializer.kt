// hash: #21168fd7
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
import me.wietlol.wietbot.clients.stackexchange.models.messages.builders.MessagePostedEventBuilder
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.*
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.MessagePostedEvent

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object MessagePostedEventSerializer : ModelSerializer<MessagePostedEvent, MessagePostedEvent>
{
	private val endOfObject: Int
		= 0
	
	private val idIndex: Int
		= 1
	
	private val timeStampIndex: Int
		= 2
	
	private val messageIdIndex: Int
		= 3
	
	private val contentIndex: Int
		= 4
	
	private val parentIdIndex: Int
		= 5
	
	private val showParentIndex: Int
		= 6
	
	private val userIndex: Int
		= 7
	
	private val roomIndex: Int
		= 8
	
	override val modelId: UUID
		get() = MessagePostedEvent.serializationKey
	
	override val dataClass: Class<MessagePostedEvent>
		get() = MessagePostedEvent::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: MessagePostedEvent)
	{
		stream.writeUnsignedVarInt(idIndex)
		schema.serialize(serializationContext, stream, entity.id)
		
		stream.writeUnsignedVarInt(timeStampIndex)
		schema.serialize(serializationContext, stream, entity.timeStamp)
		
		stream.writeUnsignedVarInt(messageIdIndex)
		schema.serialize(serializationContext, stream, entity.messageId)
		
		stream.writeUnsignedVarInt(contentIndex)
		schema.serialize(serializationContext, stream, entity.content)
		
		entity.parentId?.also {
			stream.writeUnsignedVarInt(parentIdIndex)
			schema.serialize(serializationContext, stream, it)
		}
		
		entity.showParent?.also {
			stream.writeUnsignedVarInt(showParentIndex)
			schema.serialize(serializationContext, stream, it)
		}
		
		stream.writeUnsignedVarInt(userIndex)
		schema.serialize(serializationContext, stream, entity.user)
		
		stream.writeUnsignedVarInt(roomIndex)
		schema.serialize(serializationContext, stream, entity.room)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): MessagePostedEvent
	{
		var id: Int? = null
		var timeStamp: Long? = null
		var messageId: Int? = null
		var content: String? = null
		var parentId: Int? = null
		var showParent: Boolean? = null
		var user: User? = null
		var room: Room? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return MessagePostedEventImpl(
					id!!,
					timeStamp!!,
					messageId!!,
					content!!,
					parentId,
					showParent,
					user!!,
					room!!,
				)
				idIndex -> id = schema.deserialize(deserializationContext, stream)
				timeStampIndex -> timeStamp = schema.deserialize(deserializationContext, stream)
				messageIdIndex -> messageId = schema.deserialize(deserializationContext, stream)
				contentIndex -> content = schema.deserialize(deserializationContext, stream)
				parentIdIndex -> parentId = schema.deserialize(deserializationContext, stream)
				showParentIndex -> showParent = schema.deserialize(deserializationContext, stream)
				userIndex -> user = schema.deserialize(deserializationContext, stream)
				roomIndex -> room = schema.deserialize(deserializationContext, stream)
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
