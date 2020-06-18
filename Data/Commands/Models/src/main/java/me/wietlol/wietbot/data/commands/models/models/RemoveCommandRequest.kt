package me.wietlol.wietbot.data.commands.models.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.common.emptyHashCode
import me.wietlol.common.Jsonable
import me.wietlol.common.toJson
import me.wietlol.common.with
import java.util.*
import me.wietlol.wietbot.data.commands.models.serializers.RemoveCommandRequestSerializer

interface RemoveCommandRequest : BitSerializable, Jsonable
{
	override val serializationKey: UUID
		get() = RemoveCommandRequestSerializer.modelId
	
	val keyword: String
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is RemoveCommandRequest) return false
		
		if (keyword != other.keyword) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(keyword)
	
	override fun toJson(): String =
		"""{"keyword":${keyword.toJson()}}"""
	
	companion object
	{
		fun of(keyword: String): RemoveCommandRequest =
			object : RemoveCommandRequest
			{
				override val keyword: String = keyword
				
				override fun equals(other: Any?): Boolean =
					isEqualTo(other)
				
				override fun hashCode(): Int =
					computeHashCode()
				
				override fun toString(): String =
					toJson()
			}
	}
}
