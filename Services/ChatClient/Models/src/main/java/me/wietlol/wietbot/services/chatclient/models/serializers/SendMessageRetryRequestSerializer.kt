// hash: #b4f9bffc
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
import me.wietlol.wietbot.services.chatclient.models.builders.SendMessageRetryRequestBuilder
import me.wietlol.wietbot.services.chatclient.models.models.*
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRetryRequest

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object SendMessageRetryRequestSerializer : ModelSerializer<SendMessageRetryRequest, SendMessageRetryRequest>
{
	private val endOfObject: Int
		= 0
	
	private val requestIndex: Int
		= 1
	
	private val tryCountIndex: Int
		= 2
	
	override val modelId: UUID
		get() = SendMessageRetryRequest.serializationKey
	
	override val dataClass: Class<SendMessageRetryRequest>
		get() = SendMessageRetryRequest::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: SendMessageRetryRequest)
	{
		stream.writeUnsignedVarInt(requestIndex)
		schema.serialize(serializationContext, stream, entity.request)
		
		stream.writeUnsignedVarInt(tryCountIndex)
		schema.serialize(serializationContext, stream, entity.tryCount)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): SendMessageRetryRequest
	{
		var request: SendMessageRequest? = null
		var tryCount: Int? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return SendMessageRetryRequestImpl(
					request!!,
					tryCount!!,
				)
				requestIndex -> request = schema.deserialize(deserializationContext, stream)
				tryCountIndex -> tryCount = schema.deserialize(deserializationContext, stream)
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
