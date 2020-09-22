// hash: #dc2e92b9
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
	var userId: Int?
		= null
	
	var permission: String?
		= null
	
	var resource: String?
		= "*"
	
	fun build(): IsUserAuthorizedRequest =
		IsUserAuthorizedRequestImpl(
			userId!!,
			permission!!,
			resource!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
