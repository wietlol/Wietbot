// hash: #bca18f69
// @formatter:off
package me.wietlol.wietbot.clients.stackexchange.models.messages.models

import me.wietlol.wietbot.clients.stackexchange.models.messages.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off


data class MessagePostedEventImpl(
	override val id: Int,
	override val timeStamp: Long,
	override val messageId: Int,
	override val content: String,
	override val parentId: Int?,
	override val showParent: Boolean?,
	override val user: User,
	override val room: Room,
) : MessagePostedEvent
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): MessagePostedEventImpl =
		copy(
			id = id,
			timeStamp = timeStamp,
			messageId = messageId,
			content = content,
			parentId = parentId,
			showParent = showParent,
			user = user.duplicate(),
			room = room.duplicate(),
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
