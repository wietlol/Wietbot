// hash: #394d14d1
// @formatter:off
package me.wietlol.wietbot.data.auth.models.models

import me.wietlol.wietbot.data.auth.models.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off


data class IsUserAuthorizedRequestImpl(
	override val userId: String,
	override val platform: Platform,
	override val permission: String,
	override val resource: String = "*",
) : IsUserAuthorizedRequest
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): IsUserAuthorizedRequestImpl =
		copy(
			userId = userId,
			platform = platform.duplicate(),
			permission = permission,
			resource = resource,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
