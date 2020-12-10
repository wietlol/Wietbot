// hash: #6acd2ea8
// @formatter:off
package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.DefaultCreateGrantedAuthorityRequest

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class CreateGrantedAuthorityRequestBuilder
{
	var policy: String?
		= null
	
	var permission: String?
		= null
	
	var resource: String?
		= null
	
	fun build(): CreateGrantedAuthorityRequest =
		DefaultCreateGrantedAuthorityRequest(
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
