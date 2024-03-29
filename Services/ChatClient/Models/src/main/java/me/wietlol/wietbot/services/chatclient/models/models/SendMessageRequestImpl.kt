// hash: #53611ae3
// @formatter:off
package me.wietlol.wietbot.services.chatclient.models.models

import me.wietlol.wietbot.services.chatclient.models.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn

import me.wietlol.wietbot.data.messages.models.models.Content

// @tomplot:customCode:end
// @formatter:off


data class SendMessageRequestImpl(
	override val platform: String,
	override val target: String,
	override val content: Content,
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
			platform = platform,
			target = target,
			content = content.duplicate(),
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
