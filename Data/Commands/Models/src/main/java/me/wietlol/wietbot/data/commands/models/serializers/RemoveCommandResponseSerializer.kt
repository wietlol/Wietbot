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

object RemoveCommandResponseSerializer : ModelSerializer<RemoveCommandResponse, RemoveCommandResponse>
{
	private const val endOfObject = 0
	
	override val modelId: UUID
		get() = UUID.fromString("c1649182-d518-4016-b709-acb8d386fb2f")
	override val dataClass: Class<RemoveCommandResponse>
		get() = RemoveCommandResponse::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: RemoveCommandResponse)
	{
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): RemoveCommandResponse
	{
		val builder = RemoveCommandResponseBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
			}
		}
	}
}
