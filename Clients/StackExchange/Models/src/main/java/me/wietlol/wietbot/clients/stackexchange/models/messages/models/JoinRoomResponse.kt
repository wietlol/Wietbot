// hash: #c3ed1218
// data: serializationKey:73c7df36-0e8a-40c8-a3a6-d182928fd661
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


interface JoinRoomResponse : BitSerializable, ClientCommandResponse, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("73c7df36-0e8a-40c8-a3a6-d182928fd661")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is JoinRoomResponse) return false
		
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
	
	override fun toJson(): String =
		"""{}"""
	
	override fun duplicate(): JoinRoomResponse
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
