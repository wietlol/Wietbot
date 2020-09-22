// hash: #8c96cd5b
// @formatter:off
package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.SetUserRoleRequestImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class SetUserRoleRequestBuilder
{
	var userId: Int?
		= null
	
	var role: String?
		= null
	
	fun build(): SetUserRoleRequest =
		SetUserRoleRequestImpl(
			userId!!,
			role!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
