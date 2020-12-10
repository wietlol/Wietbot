// hash: #9d118f23
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
import me.wietlol.wietbot.data.auth.models.builders.UserBuilder
import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.User

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object UserSerializer : ModelSerializer<User, User>
{
	private val endOfObject: Int
		= 0
	
	private val localIdIndex: Int
		= 1
	
	private val localNameIndex: Int
		= 2
	
	private val platformIndex: Int
		= 3
	
	private val internalIdIndex: Int
		= 4
	
	private val stackOverflowIdIndex: Int
		= 5
	
	private val stackOverflowNameIndex: Int
		= 6
	
	private val discordIdIndex: Int
		= 7
	
	private val discordNameIndex: Int
		= 8
	
	private val wietbotWebsiteIdIndex: Int
		= 9
	
	private val wietbotWebsiteNameIndex: Int
		= 10
	
	override val modelId: UUID
		get() = User.serializationKey
	
	override val dataClass: Class<User>
		get() = User::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: User)
	{
		stream.writeUnsignedVarInt(localIdIndex)
		schema.serialize(serializationContext, stream, entity.localId)
		
		stream.writeUnsignedVarInt(localNameIndex)
		schema.serialize(serializationContext, stream, entity.localName)
		
		stream.writeUnsignedVarInt(platformIndex)
		schema.serialize(serializationContext, stream, entity.platform)
		
		stream.writeUnsignedVarInt(internalIdIndex)
		schema.serialize(serializationContext, stream, entity.internalId)
		
		entity.stackOverflowId?.also {
			stream.writeUnsignedVarInt(stackOverflowIdIndex)
			schema.serialize(serializationContext, stream, it)
		}
		
		entity.stackOverflowName?.also {
			stream.writeUnsignedVarInt(stackOverflowNameIndex)
			schema.serialize(serializationContext, stream, it)
		}
		
		entity.discordId?.also {
			stream.writeUnsignedVarInt(discordIdIndex)
			schema.serialize(serializationContext, stream, it)
		}
		
		entity.discordName?.also {
			stream.writeUnsignedVarInt(discordNameIndex)
			schema.serialize(serializationContext, stream, it)
		}
		
		entity.wietbotWebsiteId?.also {
			stream.writeUnsignedVarInt(wietbotWebsiteIdIndex)
			schema.serialize(serializationContext, stream, it)
		}
		
		entity.wietbotWebsiteName?.also {
			stream.writeUnsignedVarInt(wietbotWebsiteNameIndex)
			schema.serialize(serializationContext, stream, it)
		}
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): User
	{
		var localId: String? = null
		var localName: String? = null
		var platform: Platform? = null
		var internalId: Long? = null
		var stackOverflowId: String? = null
		var stackOverflowName: String? = null
		var discordId: String? = null
		var discordName: String? = null
		var wietbotWebsiteId: String? = null
		var wietbotWebsiteName: String? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return DefaultUser(
					localId!!,
					localName!!,
					platform!!,
					internalId!!,
					stackOverflowId,
					stackOverflowName,
					discordId,
					discordName,
					wietbotWebsiteId,
					wietbotWebsiteName,
				)
				localIdIndex -> localId = schema.deserialize(deserializationContext, stream)
				localNameIndex -> localName = schema.deserialize(deserializationContext, stream)
				platformIndex -> platform = schema.deserialize(deserializationContext, stream)
				internalIdIndex -> internalId = schema.deserialize(deserializationContext, stream)
				stackOverflowIdIndex -> stackOverflowId = schema.deserialize(deserializationContext, stream)
				stackOverflowNameIndex -> stackOverflowName = schema.deserialize(deserializationContext, stream)
				discordIdIndex -> discordId = schema.deserialize(deserializationContext, stream)
				discordNameIndex -> discordName = schema.deserialize(deserializationContext, stream)
				wietbotWebsiteIdIndex -> wietbotWebsiteId = schema.deserialize(deserializationContext, stream)
				wietbotWebsiteNameIndex -> wietbotWebsiteName = schema.deserialize(deserializationContext, stream)
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
