package me.wietlol.wietbot.services.chatclient.models.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.common.emptyHashCode
import me.wietlol.common.Jsonable
import me.wietlol.common.toJson
import me.wietlol.common.with
import java.util.*
import me.wietlol.wietbot.services.chatclient.models.serializers.SendMessageRequestSerializer

interface SendMessageRequest : BitSerializable, Jsonable
{
	override val serializationKey: UUID
		get() = SendMessageRequestSerializer.modelId
	
	val roomId: Int
	val text: String
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is SendMessageRequest) return false
		
		if (roomId != other.roomId) return false
		if (text != other.text) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(roomId)
			.with(text)
	
	override fun toJson(): String =
		"""{"roomId":${roomId.toJson()},"text":${text.toJson()}}"""
	
	fun duplicate(): SendMessageRequest
	
	companion object
	{
		fun of(roomId: Int, text: String): SendMessageRequest
		{
			return Implementation(roomId, text)
		}
		
		private data class Implementation(
			override var roomId: Int,
			override var text: String
		) : SendMessageRequest
		{
			override fun equals(other: Any?): Boolean =
				isEqualTo(other)
			
			override fun hashCode(): Int =
				computeHashCode()
			
			override fun toString(): String =
				toJson()
			
			override fun duplicate(): SendMessageRequest =
				copy(
					roomId = roomId,
					text = text
				)
		}
	}
}
