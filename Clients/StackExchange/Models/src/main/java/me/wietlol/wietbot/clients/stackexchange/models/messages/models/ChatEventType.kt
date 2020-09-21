// hash: #41178ea5
// data: serializationKey:85252964-6c54-474d-bfff-be4b0865501a
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


interface ChatEventType : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("85252964-6c54-474d-bfff-be4b0865501a")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val id: Int
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is ChatEventType) return false
		
		if (id != other.id) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(id)
	
	override fun toJson(): String =
		"""{"id":${id.toJsonString()}}"""
	
	fun duplicate(): ChatEventType
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
