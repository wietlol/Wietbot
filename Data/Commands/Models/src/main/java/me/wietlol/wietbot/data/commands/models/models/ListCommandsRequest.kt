package me.wietlol.wietbot.data.commands.models.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.common.emptyHashCode
import me.wietlol.common.Jsonable
import me.wietlol.common.toJson
import me.wietlol.common.with
import java.util.*
import me.wietlol.wietbot.data.commands.models.serializers.ListCommandsRequestSerializer

interface ListCommandsRequest : BitSerializable, Jsonable
{
	override val serializationKey: UUID
		get() = ListCommandsRequestSerializer.modelId
	
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is ListCommandsRequest) return false
		
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
	
	override fun toJson(): String =
		"""{}"""
	
	companion object
	{
		fun of(): ListCommandsRequest =
			object : ListCommandsRequest
			{
				
				override fun equals(other: Any?): Boolean =
					isEqualTo(other)
				
				override fun hashCode(): Int =
					computeHashCode()
				
				override fun toString(): String =
					toJson()
			}
	}
}
