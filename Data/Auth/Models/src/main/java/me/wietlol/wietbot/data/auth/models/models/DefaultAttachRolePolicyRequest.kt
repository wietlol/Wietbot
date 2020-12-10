// hash: #e913f5c3
// @formatter:off
package me.wietlol.wietbot.data.auth.models.models

import me.wietlol.wietbot.data.auth.models.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off


data class DefaultAttachRolePolicyRequest(
	override val role: String,
	override val policy: String,
) : AttachRolePolicyRequest
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): DefaultAttachRolePolicyRequest =
		copy(
			role = role,
			policy = policy,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
