// hash: #1bf16d57
// @formatter:off
package me.wietlol.wietbot.services.chatclient.models.models

import me.wietlol.wietbot.services.chatclient.models.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off


data class EditMessageRetryRequestImpl(
	override val request: EditMessageRequest,
	override val tryCount: Int,
) : EditMessageRetryRequest
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): EditMessageRetryRequestImpl =
		copy(
			request = request.duplicate(),
			tryCount = tryCount,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
