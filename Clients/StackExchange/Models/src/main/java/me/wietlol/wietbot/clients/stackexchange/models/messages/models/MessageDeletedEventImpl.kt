// hash: #6fef93a3
// @formatter:off
package me.wietlol.wietbot.clients.stackexchange.models.messages.models

import me.wietlol.wietbot.clients.stackexchange.models.messages.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off


data class MessageDeletedEventImpl(
	override val id: Int,
	override val timeStamp: Long,
	override val messageId: Int,
	override val messageEdits: Int,
	override val user: User,
	override val room: Room,
) : MessageDeletedEvent
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): MessageDeletedEventImpl =
		copy(
			id = id,
			timeStamp = timeStamp,
			messageId = messageId,
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
