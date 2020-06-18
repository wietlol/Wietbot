package me.wietlol.wietbot.data.commands.models.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.common.emptyHashCode
import me.wietlol.common.Jsonable
import me.wietlol.common.toJson
import me.wietlol.common.with
import java.util.*
import me.wietlol.wietbot.data.commands.models.serializers.RemoveCommandResponseSerializer

interface RemoveCommandResponse : BitSerializable, Jsonable
{
	override val serializationKey: UUID
		get() = RemoveCommandResponseSerializer.modelId
	
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is RemoveCommandResponse) return false
		
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
	
	override fun toJson(): String =
		"""{}"""
	
	companion object
	{
		fun of(): RemoveCommandResponse =
			object : RemoveCommandResponse
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
