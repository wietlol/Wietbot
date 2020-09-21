// hash: #4d72cf36
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
import me.wietlol.wietbot.clients.stackexchange.models.messages.builders.ListCommandsRequestBuilder
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.*
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.ListCommandsRequest

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object ListCommandsRequestSerializer : ModelSerializer<ListCommandsRequest, ListCommandsRequest>
{
	private val endOfObject: Int
		= 0
	
	override val modelId: UUID
		get() = ListCommandsRequest.serializationKey
	
	override val dataClass: Class<ListCommandsRequest>
		get() = ListCommandsRequest::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: ListCommandsRequest)
	{
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): ListCommandsRequest
	{
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return ListCommandsRequestImpl(
				)
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
