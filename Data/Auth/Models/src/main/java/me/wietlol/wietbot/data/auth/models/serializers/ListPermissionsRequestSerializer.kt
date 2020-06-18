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

object ListPermissionsRequestSerializer : ModelSerializer<ListPermissionsRequest, ListPermissionsRequest>
{
	private const val endOfObject = 0
	
	override val modelId: UUID
		get() = UUID.fromString("68c7f9bb-0b5d-4159-a3e4-345cba6e8763")
	override val dataClass: Class<ListPermissionsRequest>
		get() = ListPermissionsRequest::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: ListPermissionsRequest)
	{
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): ListPermissionsRequest
	{
		val builder = ListPermissionsRequestBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
			}
		}
	}
}
