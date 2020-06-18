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

object ListRolesResponseSerializer : ModelSerializer<ListRolesResponse, ListRolesResponse>
{
	private const val endOfObject = 0
	private const val rolesIndex = 1
	
	override val modelId: UUID
		get() = UUID.fromString("67dc8146-e03b-4bdb-98ad-53c2367a0250")
	override val dataClass: Class<ListRolesResponse>
		get() = ListRolesResponse::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: ListRolesResponse)
	{
		if (entity.roles.isNotEmpty())
		{
			stream.writeUnsignedVarInt(rolesIndex)
			entity.roles.forEach {
				schema.serialize(stream, it)
			}
			stream.writeUnsignedVarInt(endOfObject)
		}
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): ListRolesResponse
	{
		val builder = ListRolesResponseBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
				rolesIndex ->
				{
					while (true)
					{
						val key = stream.readUnsignedVarInt()
						if (key == 0)
							break
						
						builder.roles.add(schema.deserialize(stream, key))
					}
				}
			}
		}
	}
}
