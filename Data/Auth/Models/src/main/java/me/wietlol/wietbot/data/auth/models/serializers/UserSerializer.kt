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

object UserSerializer : ModelSerializer<User, User>
{
	private const val endOfObject = 0
	private const val idIndex = 1
	private const val nameIndex = 2
	
	override val modelId: UUID
		get() = UUID.fromString("bbb57bdc-faf4-4516-945b-4deaf3faf634")
	override val dataClass: Class<User>
		get() = User::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: User)
	{
		stream.writeUnsignedVarInt(idIndex)
		schema.serialize(stream, entity.id)
		
		stream.writeUnsignedVarInt(nameIndex)
		schema.serialize(stream, entity.name)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): User
	{
		val builder = UserBuilder()
		
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
