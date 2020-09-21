// hash: #180374ba
// data: serializationKey:1f83a563-4d15-40d7-a30f-10ba726a7462
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


interface ShutdownRequest : BitSerializable, ClientCommandRequest, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("1f83a563-4d15-40d7-a30f-10ba726a7462")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is ShutdownRequest) return false
		
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
	
	override fun toJson(): String =
		"""{}"""
	
	override fun duplicate(): ShutdownRequest
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
