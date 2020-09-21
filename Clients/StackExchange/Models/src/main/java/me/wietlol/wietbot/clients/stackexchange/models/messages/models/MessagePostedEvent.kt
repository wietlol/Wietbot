// hash: #2c689240
// data: serializationKey:42cabed7-3d78-4ad9-bf7a-1cb668d8f0d6
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


interface MessagePostedEvent : BitSerializable, MessageEvent, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("42cabed7-3d78-4ad9-bf7a-1cb668d8f0d6")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	override val id: Int
	
	override val timeStamp: Long
	
	override val eventType: ChatEventType
		get() = ChatEventTypeImpl.messagePosted
	
	override val messageId: Int
	
	val content: String
	
	val parentId: Int?
	
	val showParent: Boolean?
	
	val user: User
	
	override val room: Room
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is MessagePostedEvent) return false
		
		if (id != other.id) return false
		if (timeStamp != other.timeStamp) return false
		if (eventType != other.eventType) return false
		if (messageId != other.messageId) return false
		if (content != other.content) return false
		if (parentId != other.parentId) return false
		if (showParent != other.showParent) return false
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
			.with(parentId)
			.with(showParent)
			.with(user)
			.with(room)
	
	override fun toJson(): String =
		"""{"id":${id.toJsonString()},"timeStamp":${timeStamp.toJsonString()},"eventType":${eventType.toJsonString()},"messageId":${messageId.toJsonString()},"content":${content.toJsonString()},"parentId":${parentId.toJsonString()},"showParent":${showParent.toJsonString()},"user":${user.toJsonString()},"room":${room.toJsonString()}}"""
	
	override fun duplicate(): MessagePostedEvent
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
