// hash: #0ea08a56
// @formatter:off
package me.wietlol.wietbot.data.auth.models.models

import me.wietlol.wietbot.data.auth.models.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off


data class SetUserRoleRequestImpl(
	override val localUserId: String,
	override val platform: Platform,
	override val role: String,
) : SetUserRoleRequest
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): SetUserRoleRequestImpl =
		copy(
			localUserId = localUserId,
			platform = platform.duplicate(),
			role = role,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
