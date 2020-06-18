package me.wietlol.wietbot.data.commands.models.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.common.emptyHashCode
import me.wietlol.common.Jsonable
import me.wietlol.common.toJson
import me.wietlol.common.with
import java.util.*
import me.wietlol.wietbot.data.commands.models.serializers.CreateCommandRequestSerializer

interface CreateCommandRequest : BitSerializable, Jsonable
{
	override val serializationKey: UUID
		get() = CreateCommandRequestSerializer.modelId
	
	val keyword: String
	val listenerQueue: String
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is CreateCommandRequest) return false
		
		if (keyword != other.keyword) return false
		if (listenerQueue != other.listenerQueue) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(keyword)
			.with(listenerQueue)
	
	override fun toJson(): String =
		"""{"keyword":${keyword.toJson()},"listenerQueue":${listenerQueue.toJson()}}"""
	
	companion object
	{
		fun of(keyword: String, listenerQueue: String): CreateCommandRequest =
			object : CreateCommandRequest
			{
				override val keyword: String = keyword
				override val listenerQueue: String = listenerQueue
				
				override fun equals(other: Any?): Boolean =
					isEqualTo(other)
				
				override fun hashCode(): Int =
					computeHashCode()
				
				override fun toString(): String =
					toJson()
			}
	}
}
