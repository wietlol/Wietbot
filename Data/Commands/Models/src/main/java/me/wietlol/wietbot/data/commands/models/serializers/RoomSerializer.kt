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

object RoomSerializer : ModelSerializer<Room, Room>
{
	private const val endOfObject = 0
	private const val idIndex = 1
	
	override val modelId: UUID
		get() = UUID.fromString("54a77739-9696-4244-b394-ae5efe0919ef")
	override val dataClass: Class<Room>
		get() = Room::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: Room)
	{
		stream.writeUnsignedVarInt(idIndex)
		schema.serialize(stream, entity.id)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): Room
	{
		val builder = RoomBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
				idIndex -> builder.id = schema.deserialize(stream)
			}
		}
	}
}
