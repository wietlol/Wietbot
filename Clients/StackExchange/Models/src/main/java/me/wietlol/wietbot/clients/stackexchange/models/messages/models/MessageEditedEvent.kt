// hash: #0f629db0
// data: serializationKey:39531a8e-9eae-4234-b756-66acb07a9d09
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


interface MessageEditedEvent : BitSerializable, MessageEvent, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("39531a8e-9eae-4234-b756-66acb07a9d09")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	override val id: Int
	
	override val timeStamp: Long
	
	override val eventType: ChatEventType
		get() = ChatEventTypeImpl.messageEdited
	
	override val messageId: Int
	
	val content: String
	
	val messageEdits: Int
	
	val user: User
	
	override val room: Room
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is MessageEditedEvent) return false
		
		if (id != other.id) return false
		if (timeStamp != other.timeStamp) return false
		if (eventType != other.eventType) return false
		if (messageId != other.messageId) return false
		if (content != other.content) return false
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
			.with(content)
			.with(messageEdits)
			.with(user)
			.with(room)
	
	override fun toJson(): String =
		"""{"id":${id.toJsonString()},"timeStamp":${timeStamp.toJsonString()},"eventType":${eventType.toJsonString()},"messageId":${messageId.toJsonString()},"content":${content.toJsonString()},"messageEdits":${messageEdits.toJsonString()},"user":${user.toJsonString()},"room":${room.toJsonString()}}"""
	
	override fun duplicate(): MessageEditedEvent
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
