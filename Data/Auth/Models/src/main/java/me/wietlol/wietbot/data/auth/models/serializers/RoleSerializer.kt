package me.wietlol.wietbot.data.auth.models.serializers

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

import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.builders.*

object RoleSerializer : ModelSerializer<Role, Role>
{
	private const val endOfObject = 0
	private const val idIndex = 1
	private const val nameIndex = 2
	
	override val modelId: UUID
		get() = UUID.fromString("48a3c85f-2559-47d8-83d7-67da72d7b95b")
	override val dataClass: Class<Role>
		get() = Role::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: Role)
	{
		stream.writeUnsignedVarInt(idIndex)
		schema.serialize(stream, entity.id)
		
		stream.writeUnsignedVarInt(nameIndex)
		schema.serialize(stream, entity.name)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): Role
	{
		val builder = RoleBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
				idIndex -> builder.id = schema.deserialize(stream)
				nameIndex -> builder.name = schema.deserialize(stream)
			}
		}
	}
}
