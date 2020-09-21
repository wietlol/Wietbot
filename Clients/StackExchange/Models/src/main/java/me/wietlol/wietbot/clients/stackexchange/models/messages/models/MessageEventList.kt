// hash: #4058092e
// data: serializationKey:6007fb99-26d2-490c-aadb-f94e41180678
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


interface MessageEventList : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("6007fb99-26d2-490c-aadb-f94e41180678")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val messageId: Int
	
	val events: List<MessageEvent>
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is MessageEventList) return false
		
		if (messageId != other.messageId) return false
		if (events != other.events) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(messageId)
			.with(events)
	
	override fun toJson(): String =
		"""{"messageId":${messageId.toJsonString()},"events":${events.toJsonString()}}"""
	
	fun duplicate(): MessageEventList
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
