// hash: #b2974e6b
// data: serializationKey:3dc8a4ad-c0d3-4951-b1d8-9df9e0570601
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


interface MessageDeletedEvent : BitSerializable, MessageEvent, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("3dc8a4ad-c0d3-4951-b1d8-9df9e0570601")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	override val id: Int
	
	override val timeStamp: Long
	
	override val eventType: ChatEventType
		get() = ChatEventTypeImpl.messageEdited
	
	override val messageId: Int
	
	val messageEdits: Int
	
	val user: User
	
	override val room: Room
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is MessageDeletedEvent) return false
		
		if (id != other.id) return false
		if (timeStamp != other.timeStamp) return false
		if (eventType != other.eventType) return false
		if (messageId != other.messageId) return false
		if (messageEdits != other.messageEdits) return false
		if (user != other.user) return false
		if (room != other.room) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(id)
			.with(timeStamp)
			.with(eventType)
			.with(messageId)
			.with(messageEdits)
			.with(user)
			.with(room)
	
	override fun toJson(): String =
		"""{"id":${id.toJsonString()},"timeStamp":${timeStamp.toJsonString()},"eventType":${eventType.toJsonString()},"messageId":${messageId.toJsonString()},"messageEdits":${messageEdits.toJsonString()},"user":${user.toJsonString()},"room":${room.toJsonString()}}"""
	
	override fun duplicate(): MessageDeletedEvent
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
