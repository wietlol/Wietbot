// hash: #0a5cf6b2
// @formatter:off
package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.IsUserAuthorizedRequestImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class IsUserAuthorizedRequestBuilder
{
	var userId: String?
		= null
	
	var platform: Platform?
		= null
	
	var permission: String?
		= null
	
	var resource: String?
		= "*"
	
	fun build(): IsUserAuthorizedRequest =
		IsUserAuthorizedRequestImpl(
			userId!!,
			platform!!,
			permission!!,
			resource!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
