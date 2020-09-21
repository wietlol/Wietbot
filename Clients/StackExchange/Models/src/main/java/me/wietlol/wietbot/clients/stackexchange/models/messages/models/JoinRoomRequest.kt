// hash: #fe57f187
// data: serializationKey:e4788228-2923-43ab-86e9-6d1779fb486f
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


interface JoinRoomRequest : BitSerializable, ClientCommandRequest, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("e4788228-2923-43ab-86e9-6d1779fb486f")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val roomId: Int
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is JoinRoomRequest) return false
		
		if (roomId != other.roomId) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(roomId)
	
	override fun toJson(): String =
		"""{"roomId":${roomId.toJsonString()}}"""
	
	override fun duplicate(): JoinRoomRequest
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
