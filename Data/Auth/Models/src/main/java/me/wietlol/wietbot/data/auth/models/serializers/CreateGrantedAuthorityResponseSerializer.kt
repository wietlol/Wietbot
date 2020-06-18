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

object CreateGrantedAuthorityResponseSerializer : ModelSerializer<CreateGrantedAuthorityResponse, CreateGrantedAuthorityResponse>
{
	private const val endOfObject = 0
	
	override val modelId: UUID
		get() = UUID.fromString("0c53649a-8e16-4eb5-8982-8a7f693b7e3c")
	override val dataClass: Class<CreateGrantedAuthorityResponse>
		get() = CreateGrantedAuthorityResponse::class.java
	
	override fun serialize(stream: OutputStream, schema: Schema, entity: CreateGrantedAuthorityResponse)
	{
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(stream: InputStream, schema: Schema): CreateGrantedAuthorityResponse
	{
		val builder = CreateGrantedAuthorityResponseBuilder()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return builder.build()
			}
		}
	}
}
