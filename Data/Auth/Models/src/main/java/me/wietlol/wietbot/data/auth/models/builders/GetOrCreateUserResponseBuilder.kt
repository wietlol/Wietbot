// hash: #56ecf9ee
// @formatter:off
package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.GetOrCreateUserResponseImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class GetOrCreateUserResponseBuilder
{
	var user: User?
		= null
	
	fun build(): GetOrCreateUserResponse =
		GetOrCreateUserResponseImpl(
			user!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
