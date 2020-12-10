// hash: #27ba4f79
// @formatter:off
package me.wietlol.wietbot.data.auth.models.models

import me.wietlol.wietbot.data.auth.models.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off


data class UserImpl(
	override val localId: String,
	override val localName: String,
	override val platform: Platform,
	override val internalId: Long,
	override val stackExchangeId: String,
	override val stackExchangeName: String,
	override val discordId: String,
	override val discordName: String,
	override val wietbotWebsiteId: String,
	override val wietbotWebsiteName: String,
	override val role: Int,
) : User
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): UserImpl =
		copy(
			localId = localId,
			localName = localName,
			platform = platform.duplicate(),
			internalId = internalId,
			stackExchangeId = stackExchangeId,
			stackExchangeName = stackExchangeName,
			discordId = discordId,
			discordName = discordName,
			wietbotWebsiteId = wietbotWebsiteId,
			wietbotWebsiteName = wietbotWebsiteName,
			role = role,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
