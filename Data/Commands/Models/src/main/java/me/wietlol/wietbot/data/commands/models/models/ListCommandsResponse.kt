package me.wietlol.wietbot.data.commands.models.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.common.emptyHashCode
import me.wietlol.common.Jsonable
import me.wietlol.common.toJson
import me.wietlol.common.with
import java.util.*
import me.wietlol.wietbot.data.commands.models.serializers.ListCommandsResponseSerializer

interface ListCommandsResponse : BitSerializable, Jsonable
{
	override val serializationKey: UUID
		get() = ListCommandsResponseSerializer.modelId
	
	val commands: List<Command>
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is ListCommandsResponse) return false
		
		if (commands != other.commands) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(commands)
	
	override fun toJson(): String =
		"""{"commands":${commands.toJson()}}"""
	
	companion object
	{
		fun of(commands: List<Command>): ListCommandsResponse =
			object : ListCommandsResponse
			{
				override val commands: List<Command> = commands
				
				override fun equals(other: Any?): Boolean =
					isEqualTo(other)
				
				override fun hashCode(): Int =
					computeHashCode()
				
				override fun toString(): String =
					toJson()
			}
	}
}
