// hash: #2eeb87b9
// data: serializationKey:15f20a75-d1d9-460a-9d64-8f65aec9946f
// @formatter:off
package me.wietlol.wietbot.clients.stackexchange.models.messages.models

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


interface ListCommandsResponse : BitSerializable, ClientCommandResponse, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("15f20a75-d1d9-460a-9d64-8f65aec9946f")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val commands: List<String>
	
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
	
	override fun duplicate(): ListCommandsResponse
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
