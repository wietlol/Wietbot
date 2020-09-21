// hash: #d82e08de
// data: serializationKey:0533d9be-8524-45dd-96e2-0db42f677aa1
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


interface LeaveRoomRequest : BitSerializable, ClientCommandRequest, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("0533d9be-8524-45dd-96e2-0db42f677aa1")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val roomId: Int
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is LeaveRoomRequest) return false
		
		if (roomId != other.roomId) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(roomId)
	
	override fun toJson(): String =
		"""{"roomId":${roomId.toJsonString()}}"""
	
	override fun duplicate(): LeaveRoomRequest
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
