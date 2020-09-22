// hash: #26a1e974
// @formatter:off
package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.GetOrCreateUserRequestImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class GetOrCreateUserRequestBuilder
{
	var user: User?
		= null
	
	fun build(): GetOrCreateUserRequest =
		GetOrCreateUserRequestImpl(
			user!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
