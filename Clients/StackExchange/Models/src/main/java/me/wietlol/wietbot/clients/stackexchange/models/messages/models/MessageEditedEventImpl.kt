// hash: #e392aa61
// @formatter:off
package me.wietlol.wietbot.clients.stackexchange.models.messages.models

import me.wietlol.wietbot.clients.stackexchange.models.messages.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off


data class MessageEditedEventImpl(
	override val id: Int,
	override val timeStamp: Long,
	override val messageId: Int,
	override val content: String,
	override val messageEdits: Int,
	override val user: User,
	override val room: Room,
) : MessageEditedEvent
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): MessageEditedEventImpl =
		copy(
			id = id,
			timeStamp = timeStamp,
			messageId = messageId,
			content = content,
			messageEdits = messageEdits,
			user = user.duplicate(),
			room = room.duplicate(),
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
