// hash: #7ca9e73b
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
	
	private val stackExchangeIdIndex: Int
		= 5
	
	private val stackExchangeNameIndex: Int
		= 6
	
	private val discordIdIndex: Int
		= 7
	
	private val discordNameIndex: Int
		= 8
	
	private val wietbotWebsiteIdIndex: Int
		= 9
	
	private val wietbotWebsiteNameIndex: Int
		= 10
	
	private val roleIndex: Int
		= 11
	
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
		
		stream.writeUnsignedVarInt(stackExchangeIdIndex)
		schema.serialize(serializationContext, stream, entity.stackExchangeId)
		
		stream.writeUnsignedVarInt(stackExchangeNameIndex)
		schema.serialize(serializationContext, stream, entity.stackExchangeName)
		
		stream.writeUnsignedVarInt(discordIdIndex)
		schema.serialize(serializationContext, stream, entity.discordId)
		
		stream.writeUnsignedVarInt(discordNameIndex)
		schema.serialize(serializationContext, stream, entity.discordName)
		
		stream.writeUnsignedVarInt(wietbotWebsiteIdIndex)
		schema.serialize(serializationContext, stream, entity.wietbotWebsiteId)
		
		stream.writeUnsignedVarInt(wietbotWebsiteNameIndex)
		schema.serialize(serializationContext, stream, entity.wietbotWebsiteName)
		
		stream.writeUnsignedVarInt(roleIndex)
		schema.serialize(serializationContext, stream, entity.role)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): User
	{
		var localId: String? = null
		var localName: String? = null
		var platform: Platform? = null
		var internalId: Long? = null
		var stackExchangeId: String? = null
		var stackExchangeName: String? = null
		var discordId: String? = null
		var discordName: String? = null
		var wietbotWebsiteId: String? = null
		var wietbotWebsiteName: String? = null
		var role: Int? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return UserImpl(
					localId!!,
					localName!!,
					platform!!,
					internalId!!,
					stackExchangeId!!,
					stackExchangeName!!,
					discordId!!,
					discordName!!,
					wietbotWebsiteId!!,
					wietbotWebsiteName!!,
					role!!,
				)
				localIdIndex -> localId = schema.deserialize(deserializationContext, stream)
				localNameIndex -> localName = schema.deserialize(deserializationContext, stream)
				platformIndex -> platform = schema.deserialize(deserializationContext, stream)
				internalIdIndex -> internalId = schema.deserialize(deserializationContext, stream)
				stackExchangeIdIndex -> stackExchangeId = schema.deserialize(deserializationContext, stream)
				stackExchangeNameIndex -> stackExchangeName = schema.deserialize(deserializationContext, stream)
				discordIdIndex -> discordId = schema.deserialize(deserializationContext, stream)
				discordNameIndex -> discordName = schema.deserialize(deserializationContext, stream)
				wietbotWebsiteIdIndex -> wietbotWebsiteId = schema.deserialize(deserializationContext, stream)
				wietbotWebsiteNameIndex -> wietbotWebsiteName = schema.deserialize(deserializationContext, stream)
				roleIndex -> role = schema.deserialize(deserializationContext, stream)
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
