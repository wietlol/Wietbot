// hash: #fcd2ce89
// @formatter:off
package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.IsUserAuthorizedResponseImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class IsUserAuthorizedResponseBuilder
{
	var isAuthorized: Boolean?
		= null
	
	fun build(): IsUserAuthorizedResponse =
		IsUserAuthorizedResponseImpl(
			isAuthorized!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
