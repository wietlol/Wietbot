// hash: #599021af
// @formatter:off
package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.DefaultDetachRolePolicyRequest

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class DetachRolePolicyRequestBuilder
{
	var role: String?
		= null
	
	var policy: String?
		= null
	
	fun build(): DetachRolePolicyRequest =
		DefaultDetachRolePolicyRequest(
			role!!,
			policy!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
