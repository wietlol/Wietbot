// hash: #c1daa3a9
// @formatter:off
package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.AttachRolePolicyRequestImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class AttachRolePolicyRequestBuilder
{
	var role: String?
		= null
	
	var policy: String?
		= null
	
	fun build(): AttachRolePolicyRequest =
		AttachRolePolicyRequestImpl(
			role!!,
			policy!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
