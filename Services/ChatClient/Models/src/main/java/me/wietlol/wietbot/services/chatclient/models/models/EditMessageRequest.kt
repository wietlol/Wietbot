package me.wietlol.wietbot.services.chatclient.models.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.common.emptyHashCode
import me.wietlol.common.Jsonable
import me.wietlol.common.toJson
import me.wietlol.common.with
import java.util.*
import me.wietlol.wietbot.services.chatclient.models.serializers.EditMessageRequestSerializer

interface EditMessageRequest : BitSerializable, Jsonable
{
	override val serializationKey: UUID
		get() = EditMessageRequestSerializer.modelId
	
	val messageId: Int
	val text: String
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is EditMessageRequest) return false
		
		if (messageId != other.messageId) return false
		if (text != other.text) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(messageId)
			.with(text)
	
	override fun toJson(): String =
		"""{"messageId":${messageId.toJson()},"text":${text.toJson()}}"""
	
	fun duplicate(): EditMessageRequest
	
	companion object
	{
		fun of(messageId: Int, text: String): EditMessageRequest
		{
			return Implementation(messageId, text)
		}
		
		private data class Implementation(
			override var messageId: Int,
			override var text: String
		) : EditMessageRequest
		{
			override fun equals(other: Any?): Boolean =
				isEqualTo(other)
			
			override fun hashCode(): Int =
				computeHashCode()
			
			override fun toString(): String =
				toJson()
			
			override fun duplicate(): EditMessageRequest =
				copy(
					messageId = messageId,
					text = text
				)
		}
	}
}
