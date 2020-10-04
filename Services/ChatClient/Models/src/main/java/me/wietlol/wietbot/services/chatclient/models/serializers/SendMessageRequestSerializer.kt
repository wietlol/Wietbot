// hash: #ffc2d773
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
import me.wietlol.wietbot.services.chatclient.models.builders.SendMessageRequestBuilder
import me.wietlol.wietbot.services.chatclient.models.models.*
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequest

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object SendMessageRequestSerializer : ModelSerializer<SendMessageRequest, SendMessageRequest>
{
	private val endOfObject: Int
		= 0
	
	private val platformIndex: Int
		= 1
	
	private val targetIndex: Int
		= 2
	
	private val contentIndex: Int
		= 3
	
	override val modelId: UUID
		get() = SendMessageRequest.serializationKey
	
	override val dataClass: Class<SendMessageRequest>
		get() = SendMessageRequest::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: SendMessageRequest)
	{
		stream.writeUnsignedVarInt(platformIndex)
		schema.serialize(serializationContext, stream, entity.platform)
		
		stream.writeUnsignedVarInt(targetIndex)
		schema.serialize(serializationContext, stream, entity.target)
		
		stream.writeUnsignedVarInt(contentIndex)
		schema.serialize(serializationContext, stream, entity.content)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): SendMessageRequest
	{
		var platform: String? = null
		var target: String? = null
		var content: Content? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return SendMessageRequestImpl(
					platform!!,
					target!!,
					content!!,
				)
				platformIndex -> platform = schema.deserialize(deserializationContext, stream)
				targetIndex -> target = schema.deserialize(deserializationContext, stream)
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
