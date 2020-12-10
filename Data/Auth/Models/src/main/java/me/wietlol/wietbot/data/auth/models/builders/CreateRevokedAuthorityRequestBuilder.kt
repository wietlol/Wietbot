// hash: #35179e42
// @formatter:off
package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.DefaultCreateRevokedAuthorityRequest

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class CreateRevokedAuthorityRequestBuilder
{
	var policy: String?
		= null
	
	var permission: String?
		= null
	
	var resource: String?
		= null
	
	fun build(): CreateRevokedAuthorityRequest =
		DefaultCreateRevokedAuthorityRequest(
			policy!!,
			permission!!,
			resource!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
