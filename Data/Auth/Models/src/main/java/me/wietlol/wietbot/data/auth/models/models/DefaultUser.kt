// hash: #da4d1878
// @formatter:off
package me.wietlol.wietbot.data.auth.models.models

import me.wietlol.wietbot.data.auth.models.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off


data class DefaultUser(
	override val localId: String,
	override val localName: String,
	override val platform: Platform,
	override val internalId: Long,
	override val stackOverflowId: String?,
	override val stackOverflowName: String?,
	override val discordId: String?,
	override val discordName: String?,
	override val wietbotWebsiteId: String?,
	override val wietbotWebsiteName: String?,
) : User
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): DefaultUser =
		copy(
			localId = localId,
			localName = localName,
			platform = platform.duplicate(),
			internalId = internalId,
			stackOverflowId = stackOverflowId,
			stackOverflowName = stackOverflowName,
			discordId = discordId,
			discordName = discordName,
			wietbotWebsiteId = wietbotWebsiteId,
			wietbotWebsiteName = wietbotWebsiteName,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
