// hash: #da4dd6e2
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
import me.wietlol.wietbot.clients.stackexchange.models.messages.builders.GetInfoResponseBuilder
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.*
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.GetInfoResponse

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object GetInfoResponseSerializer : ModelSerializer<GetInfoResponse, GetInfoResponse>
{
	private val endOfObject: Int
		= 0
	
	private val nameIndex: Int
		= 1
	
	private val architectureIndex: Int
		= 2
	
	private val versionIndex: Int
		= 3
	
	private val processorsIndex: Int
		= 4
	
	private val usedMemoryIndex: Int
		= 5
	
	private val maxMemoryIndex: Int
		= 6
	
	private val runtimeSinceIndex: Int
		= 7
	
	private val clientIsRunningIndex: Int
		= 8
	
	private val clientSinceIndex: Int
		= 9
	
	override val modelId: UUID
		get() = GetInfoResponse.serializationKey
	
	override val dataClass: Class<GetInfoResponse>
		get() = GetInfoResponse::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: GetInfoResponse)
	{
		stream.writeUnsignedVarInt(nameIndex)
		schema.serialize(serializationContext, stream, entity.name)
		
		stream.writeUnsignedVarInt(architectureIndex)
		schema.serialize(serializationContext, stream, entity.architecture)
		
		stream.writeUnsignedVarInt(versionIndex)
		schema.serialize(serializationContext, stream, entity.version)
		
		stream.writeUnsignedVarInt(processorsIndex)
		schema.serialize(serializationContext, stream, entity.processors)
		
		stream.writeUnsignedVarInt(usedMemoryIndex)
		schema.serialize(serializationContext, stream, entity.usedMemory)
		
		stream.writeUnsignedVarInt(maxMemoryIndex)
		schema.serialize(serializationContext, stream, entity.maxMemory)
		
		stream.writeUnsignedVarInt(runtimeSinceIndex)
		schema.serialize(serializationContext, stream, entity.runtimeSince)
		
		stream.writeUnsignedVarInt(clientIsRunningIndex)
		schema.serialize(serializationContext, stream, entity.clientIsRunning)
		
		stream.writeUnsignedVarInt(clientSinceIndex)
		schema.serialize(serializationContext, stream, entity.clientSince)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): GetInfoResponse
	{
		var name: String? = null
		var architecture: String? = null
		var version: String? = null
		var processors: Int? = null
		var usedMemory: Long? = null
		var maxMemory: Long? = null
		var runtimeSince: Long? = null
		var clientIsRunning: Boolean? = null
		var clientSince: Long? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return GetInfoResponseImpl(
					name!!,
					architecture!!,
					version!!,
					processors!!,
					usedMemory!!,
					maxMemory!!,
					runtimeSince!!,
					clientIsRunning!!,
					clientSince!!,
				)
				nameIndex -> name = schema.deserialize(deserializationContext, stream)
				architectureIndex -> architecture = schema.deserialize(deserializationContext, stream)
				versionIndex -> version = schema.deserialize(deserializationContext, stream)
				processorsIndex -> processors = schema.deserialize(deserializationContext, stream)
				usedMemoryIndex -> usedMemory = schema.deserialize(deserializationContext, stream)
				maxMemoryIndex -> maxMemory = schema.deserialize(deserializationContext, stream)
				runtimeSinceIndex -> runtimeSince = schema.deserialize(deserializationContext, stream)
				clientIsRunningIndex -> clientIsRunning = schema.deserialize(deserializationContext, stream)
				clientSinceIndex -> clientSince = schema.deserialize(deserializationContext, stream)
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
