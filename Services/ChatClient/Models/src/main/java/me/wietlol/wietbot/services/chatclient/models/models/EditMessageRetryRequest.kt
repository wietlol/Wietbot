package me.wietlol.wietbot.services.chatclient.models.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.common.emptyHashCode
import me.wietlol.common.Jsonable
import me.wietlol.common.toJson
import me.wietlol.common.with
import java.util.*
import me.wietlol.wietbot.services.chatclient.models.serializers.EditMessageRetryRequestSerializer

interface EditMessageRetryRequest : BitSerializable, Jsonable
{
	override val serializationKey: UUID
		get() = EditMessageRetryRequestSerializer.modelId
	
	val request: EditMessageRequest
	val tryCount: Int
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is EditMessageRetryRequest) return false
		
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
	
	fun duplicate(): EditMessageRetryRequest
	
	companion object
	{
		fun of(request: EditMessageRequest, tryCount: Int): EditMessageRetryRequest
		{
			return Implementation(request, tryCount)
		}
		
		private data class Implementation(
			override var request: EditMessageRequest,
			override var tryCount: Int
		) : EditMessageRetryRequest
		{
			override fun equals(other: Any?): Boolean =
				isEqualTo(other)
			
			override fun hashCode(): Int =
				computeHashCode()
			
			override fun toString(): String =
				toJson()
			
			override fun duplicate(): EditMessageRetryRequest =
				copy(
					request = request.duplicate(),
					tryCount = tryCount
				)
		}
	}
}
