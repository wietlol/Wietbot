package me.wietlol.wietbot.data.commands.models.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.common.emptyHashCode
import me.wietlol.common.Jsonable
import me.wietlol.common.toJson
import me.wietlol.common.with
import java.util.*
import me.wietlol.wietbot.data.commands.models.serializers.RoomSerializer

interface Room : BitSerializable, Jsonable
{
	override val serializationKey: UUID
		get() = RoomSerializer.modelId
	
	val id: Int
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is Room) return false
		
		if (id != other.id) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(id)
	
	override fun toJson(): String =
		"""{"id":${id.toJson()}}"""
	
	companion object
	{
		fun of(id: Int): Room =
			object : Room
			{
				override val id: Int = id
				
				override fun equals(other: Any?): Boolean =
					isEqualTo(other)
				
				override fun hashCode(): Int =
					computeHashCode()
				
				override fun toString(): String =
					toJson()
			}
	}
}
