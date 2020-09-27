// hash: #d7e464f6
// @formatter:off
package me.wietlol.wietbot.services.chatclient.models.models

import me.wietlol.wietbot.services.chatclient.models.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off


data class SendMessageRequestImpl(
	override val roomId: Int,
	override val text: String,
) : SendMessageRequest
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): SendMessageRequestImpl =
		copy(
			roomId = roomId,
			text = text,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
