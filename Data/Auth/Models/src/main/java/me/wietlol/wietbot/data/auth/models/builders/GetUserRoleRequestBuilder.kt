// hash: #84c787df
// @formatter:off
package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.GetUserRoleRequestImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class GetUserRoleRequestBuilder
{
	var localUserId: String?
		= null
	
	var platform: Platform?
		= null
	
	fun build(): GetUserRoleRequest =
		GetUserRoleRequestImpl(
			localUserId!!,
			platform!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
