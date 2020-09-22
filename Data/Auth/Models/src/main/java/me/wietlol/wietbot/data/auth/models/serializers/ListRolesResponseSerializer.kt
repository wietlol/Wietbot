// hash: #1851437a
// @formatter:off
package me.wietlol.wietbot.data.auth.models.serializers

import java.io.InputStream
import java.io.OutputStream
import java.util.UUID
import me.wietlol.bitblock.api.serialization.DeserializationContext
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.SerializationContext
import me.wietlol.bitblock.api.serialization.deserialize
import me.wietlol.utils.common.streams.readUnsignedVarInt
import me.wietlol.utils.common.streams.writeUnsignedVarInt
import me.wietlol.wietbot.data.auth.models.builders.ListRolesResponseBuilder
import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.ListRolesResponse

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object ListRolesResponseSerializer : ModelSerializer<ListRolesResponse, ListRolesResponse>
{
	private val endOfObject: Int
		= 0
	
	private val rolesIndex: Int
		= 1
	
	override val modelId: UUID
		get() = ListRolesResponse.serializationKey
	
	override val dataClass: Class<ListRolesResponse>
		get() = ListRolesResponse::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: ListRolesResponse)
	{
		stream.writeUnsignedVarInt(rolesIndex)
		schema.serialize(serializationContext, stream, entity.roles)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): ListRolesResponse
	{
		var roles: MutableList<Role>? = mutableListOf()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return ListRolesResponseImpl(
					roles!!.toMutableList(),
				)
				rolesIndex -> roles = schema.deserialize(deserializationContext, stream)
				else -> schema.deserialize<Any>(deserializationContext, stream)
			}
		}
	}
	
	// @formatter:on
	// @tomplot:customCode:start:5CFs54
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
