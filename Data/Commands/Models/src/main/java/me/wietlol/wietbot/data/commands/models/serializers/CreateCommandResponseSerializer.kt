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

object CreateCommandResponseSerializer : ModelSerializer<CreateCommandResponse, CreateCommandResponse>
{
	private const val endOfObject = 0
	
	override val modelId: UUID
		get() = UUID.fromString("f410ddd2-41ff-40aa-96dc-f554b8fe24de")
	override val dataClass: Class<CreateCommandResponse>
		get() = CreateCommandResponse::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: CreateCommandResponse)
	{
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): CreateCommandResponse
	{
		val builder = CreateCommandResponseBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
			}
		}
	}
}
