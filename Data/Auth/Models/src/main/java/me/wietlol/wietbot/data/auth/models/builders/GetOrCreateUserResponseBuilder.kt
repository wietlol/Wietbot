// hash: #4c8eb856
// @formatter:off
package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.DefaultGetOrCreateUserResponse

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class GetOrCreateUserResponseBuilder
{
	var user: User?
		= null
	
	fun build(): GetOrCreateUserResponse =
		DefaultGetOrCreateUserResponse(
			user!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
