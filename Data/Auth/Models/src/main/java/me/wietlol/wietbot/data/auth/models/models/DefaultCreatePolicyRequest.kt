// hash: #85e828ae
// @formatter:off
package me.wietlol.wietbot.data.auth.models.models

import me.wietlol.wietbot.data.auth.models.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off


data class DefaultCreatePolicyRequest(
	override val name: String,
) : CreatePolicyRequest
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): DefaultCreatePolicyRequest =
		copy(
			name = name,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
