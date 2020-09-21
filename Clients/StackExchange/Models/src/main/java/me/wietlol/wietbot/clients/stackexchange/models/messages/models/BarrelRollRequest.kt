// hash: #21ca0299
// data: serializationKey:65d0f611-e92b-4d22-8984-872fe3ecf84a
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


interface BarrelRollRequest : BitSerializable, ClientCommandRequest, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("65d0f611-e92b-4d22-8984-872fe3ecf84a")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val roomId: Int
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is BarrelRollRequest) return false
		
		if (roomId != other.roomId) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(roomId)
	
	override fun toJson(): String =
		"""{"roomId":${roomId.toJsonString()}}"""
	
	override fun duplicate(): BarrelRollRequest
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
