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

object ListPermissionsResponseSerializer : ModelSerializer<ListPermissionsResponse, ListPermissionsResponse>
{
	private const val endOfObject = 0
	private const val isAuthorizedIndex = 1
	
	override val modelId: UUID
		get() = UUID.fromString("689bc901-3f28-44bf-9e43-76c80b5a73e6")
	override val dataClass: Class<ListPermissionsResponse>
		get() = ListPermissionsResponse::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: ListPermissionsResponse)
	{
		if (entity.isAuthorized.isNotEmpty())
		{
			stream.writeUnsignedVarInt(isAuthorizedIndex)
			entity.isAuthorized.forEach {
				schema.serialize(stream, it)
			}
			stream.writeUnsignedVarInt(endOfObject)
		}
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): ListPermissionsResponse
	{
		val builder = ListPermissionsResponseBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
				isAuthorizedIndex ->
				{
					while (true)
					{
						val key = stream.readUnsignedVarInt()
						if (key == 0)
							break
						
						builder.isAuthorized.add(schema.deserialize(stream, key))
					}
				}
			}
		}
	}
}
