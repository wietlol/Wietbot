package me.wietlol.wietbot.services.chatclient.models.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.common.emptyHashCode
import me.wietlol.common.Jsonable
import me.wietlol.common.toJson
import me.wietlol.common.with
import java.util.*
import me.wietlol.wietbot.services.chatclient.models.serializers.SendMessageResponseSerializer

interface SendMessageResponse : BitSerializable, Jsonable
{
	override val serializationKey: UUID
		get() = SendMessageResponseSerializer.modelId
	
	val id: Int
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is SendMessageResponse) return false
		
		if (id != other.id) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(id)
	
	override fun toJson(): String =
		"""{"id":${id.toJson()}}"""
	
	fun duplicate(): SendMessageResponse
	
	companion object
	{
		fun of(id: Int): SendMessageResponse
		{
			return Implementation(id)
		}
		
		private data class Implementation(
			override var id: Int
		) : SendMessageResponse
		{
			override fun equals(other: Any?): Boolean =
				isEqualTo(other)
			
			override fun hashCode(): Int =
				computeHashCode()
			
			override fun toString(): String =
				toJson()
			
			override fun duplicate(): SendMessageResponse =
				copy(
					id = id
				)
		}
	}
}
