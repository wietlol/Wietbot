// hash: #c66cfb01
// @formatter:off
package me.wietlol.wietbot.clients.stackexchange.models.messages.serializers

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
import me.wietlol.wietbot.clients.stackexchange.models.messages.builders.UserBuilder
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.*
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.User

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object UserSerializer : ModelSerializer<User, User>
{
	private val endOfObject: Int
		= 0
	
	private val idIndex: Int
		= 1
	
	private val nameIndex: Int
		= 2
	
	override val modelId: UUID
		get() = User.serializationKey
	
	override val dataClass: Class<User>
		get() = User::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: User)
	{
		stream.writeUnsignedVarInt(idIndex)
		schema.serialize(serializationContext, stream, entity.id)
		
		stream.writeUnsignedVarInt(nameIndex)
		schema.serialize(serializationContext, stream, entity.name)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): User
	{
		var id: Int? = null
		var name: String? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return UserImpl(
					id!!,
					name!!,
				)
				idIndex -> id = schema.deserialize(deserializationContext, stream)
				nameIndex -> name = schema.deserialize(deserializationContext, stream)
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
