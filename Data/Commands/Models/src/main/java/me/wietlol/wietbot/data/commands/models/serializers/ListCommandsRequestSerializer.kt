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

object ListCommandsRequestSerializer : ModelSerializer<ListCommandsRequest, ListCommandsRequest>
{
	private const val endOfObject = 0
	
	override val modelId: UUID
		get() = UUID.fromString("b2857add-eedd-4f58-bfcb-5106f5673af8")
	override val dataClass: Class<ListCommandsRequest>
		get() = ListCommandsRequest::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: ListCommandsRequest)
	{
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): ListCommandsRequest
	{
		val builder = ListCommandsRequestBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
			}
		}
	}
}
