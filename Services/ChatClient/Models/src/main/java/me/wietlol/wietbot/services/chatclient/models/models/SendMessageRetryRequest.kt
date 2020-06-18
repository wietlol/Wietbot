package me.wietlol.wietbot.services.chatclient.models.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.common.emptyHashCode
import me.wietlol.common.Jsonable
import me.wietlol.common.toJson
import me.wietlol.common.with
import java.util.*
import me.wietlol.wietbot.services.chatclient.models.serializers.SendMessageRetryRequestSerializer

interface SendMessageRetryRequest : BitSerializable, Jsonable
{
	override val serializationKey: UUID
		get() = SendMessageRetryRequestSerializer.modelId
	
	val request: SendMessageRequest
	val tryCount: Int
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is SendMessageRetryRequest) return false
		
		if (request != other.request) return false
		if (tryCount != other.tryCount) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(request)
			.with(tryCount)
	
	override fun toJson(): String =
		"""{"request":${request.toJson()},"tryCount":${tryCount.toJson()}}"""
	
	fun duplicate(): SendMessageRetryRequest
	
	companion object
	{
		fun of(request: SendMessageRequest, tryCount: Int): SendMessageRetryRequest
		{
			return Implementation(request, tryCount)
		}
		
		private data class Implementation(
			override var request: SendMessageRequest,
			override var tryCount: Int
		) : SendMessageRetryRequest
		{
			override fun equals(other: Any?): Boolean =
				isEqualTo(other)
			
			override fun hashCode(): Int =
				computeHashCode()
			
			override fun toString(): String =
				toJson()
			
			override fun duplicate(): SendMessageRetryRequest =
				copy(
					request = request.duplicate(),
					tryCount = tryCount
				)
		}
	}
}
