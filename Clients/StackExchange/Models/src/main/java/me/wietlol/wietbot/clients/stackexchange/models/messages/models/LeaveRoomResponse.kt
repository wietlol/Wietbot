// hash: #3682b73c
// data: serializationKey:dd1a1699-8b50-4402-aaf0-273d8cc57e57
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


interface LeaveRoomResponse : BitSerializable, ClientCommandResponse, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("dd1a1699-8b50-4402-aaf0-273d8cc57e57")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is LeaveRoomResponse) return false
		
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
	
	override fun toJson(): String =
		"""{}"""
	
	override fun duplicate(): LeaveRoomResponse
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
