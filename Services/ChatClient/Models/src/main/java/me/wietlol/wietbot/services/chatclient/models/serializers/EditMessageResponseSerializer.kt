package me.wietlol.wietbot.services.chatclient.models.serializers

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

import me.wietlol.wietbot.services.chatclient.models.models.*
import me.wietlol.wietbot.services.chatclient.models.builders.*

object EditMessageResponseSerializer : ModelSerializer<EditMessageResponse, EditMessageResponse>
{
	private const val endOfObject = 0
	
	override val modelId: UUID
		get() = UUID.fromString("57307dd2-5173-4648-a2df-3af6e64c8d97")
	override val dataClass: Class<EditMessageResponse>
		get() = EditMessageResponse::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: EditMessageResponse)
	{
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): EditMessageResponse
	{
		val builder = EditMessageResponseBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
			}
		}
	}
}
