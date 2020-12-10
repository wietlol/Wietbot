// hash: #0f94ac8b
// @formatter:off
package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.DefaultAttachRolePolicyRequest

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
		DefaultAttachRolePolicyRequest(
			role!!,
			policy!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
