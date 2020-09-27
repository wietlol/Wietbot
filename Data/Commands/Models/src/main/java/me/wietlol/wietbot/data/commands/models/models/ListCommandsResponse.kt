// hash: #0ffafd12
// data: serializationKey:0773338a-79f0-4f1d-99ab-7bbd91dea9db
// @formatter:off
package me.wietlol.wietbot.data.commands.models.models

import java.util.UUID
import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.utils.common.Jsonable
import me.wietlol.utils.common.emptyHashCode
import me.wietlol.utils.common.toJsonString
import me.wietlol.utils.common.with

// @formatter:on
// @tomplot:customCode:start:gAeCSq
// @tomplot:customCode:end
// @formatter:off


interface ListCommandsResponse : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("0773338a-79f0-4f1d-99ab-7bbd91dea9db")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
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
		"""{"commands":${commands.toJsonString()}}"""
	
	fun duplicate(): ListCommandsResponse
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
