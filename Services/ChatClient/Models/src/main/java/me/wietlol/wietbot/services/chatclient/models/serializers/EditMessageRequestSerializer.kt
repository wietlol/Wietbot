// hash: #1a02e056
// @formatter:off
package me.wietlol.wietbot.services.chatclient.models.serializers

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
import me.wietlol.wietbot.data.messages.models.models.Content
import me.wietlol.wietbot.services.chatclient.models.builders.EditMessageRequestBuilder
import me.wietlol.wietbot.services.chatclient.models.models.*
import me.wietlol.wietbot.services.chatclient.models.models.EditMessageRequest

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object EditMessageRequestSerializer : ModelSerializer<EditMessageRequest, EditMessageRequest>
{
	private val endOfObject: Int
		= 0
	
	private val platformIndex: Int
		= 1
	
	private val messageIdIndex: Int
		= 2
	
	private val contentIndex: Int
		= 3
	
	override val modelId: UUID
		get() = EditMessageRequest.serializationKey
	
	override val dataClass: Class<EditMessageRequest>
		get() = EditMessageRequest::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: EditMessageRequest)
	{
		stream.writeUnsignedVarInt(platformIndex)
		schema.serialize(serializationContext, stream, entity.platform)
		
		stream.writeUnsignedVarInt(messageIdIndex)
		schema.serialize(serializationContext, stream, entity.messageId)
		
		stream.writeUnsignedVarInt(contentIndex)
		schema.serialize(serializationContext, stream, entity.content)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): EditMessageRequest
	{
		var platform: String? = null
		var messageId: String? = null
		var content: Content? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return EditMessageRequestImpl(
					platform!!,
					messageId!!,
					content!!,
				)
				platformIndex -> platform = schema.deserialize(deserializationContext, stream)
				messageIdIndex -> messageId = schema.deserialize(deserializationContext, stream)
				contentIndex -> content = schema.deserialize(deserializationContext, stream)
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
