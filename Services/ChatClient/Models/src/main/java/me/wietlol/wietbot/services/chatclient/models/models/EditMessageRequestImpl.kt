// hash: #743eb6a0
// @formatter:off
package me.wietlol.wietbot.services.chatclient.models.models

import me.wietlol.wietbot.services.chatclient.models.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn

import me.wietlol.wietbot.data.messages.models.models.Content

// @tomplot:customCode:end
// @formatter:off


data class EditMessageRequestImpl(
	override val platform: String,
	override val messageId: String,
	override val content: Content,
) : EditMessageRequest
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): EditMessageRequestImpl =
		copy(
			platform = platform,
			messageId = messageId,
			content = content.duplicate(),
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
