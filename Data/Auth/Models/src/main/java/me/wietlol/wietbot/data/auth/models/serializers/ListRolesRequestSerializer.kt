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

object ListRolesRequestSerializer : ModelSerializer<ListRolesRequest, ListRolesRequest>
{
	private const val endOfObject = 0
	
	override val modelId: UUID
		get() = UUID.fromString("9347829f-11e7-41ad-9ebb-78aa4dfc5866")
	override val dataClass: Class<ListRolesRequest>
		get() = ListRolesRequest::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: ListRolesRequest)
	{
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): ListRolesRequest
	{
		val builder = ListRolesRequestBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
			}
		}
	}
}
