// hash: #114776ec
// @formatter:off
package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.DefaultSetUserRoleRequest

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class SetUserRoleRequestBuilder
{
	var localUserId: String?
		= null
	
	var platform: Platform?
		= null
	
	var role: String?
		= null
	
	fun build(): SetUserRoleRequest =
		DefaultSetUserRoleRequest(
			localUserId!!,
			platform!!,
			role!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
