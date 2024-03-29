// hash: #2a073b5a
// @formatter:off
package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.DefaultGetOrCreateUserRequest

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class GetOrCreateUserRequestBuilder
{
	var localId: String?
		= null
	
	var localName: String?
		= null
	
	var platform: Platform?
		= null
	
	fun build(): GetOrCreateUserRequest =
		DefaultGetOrCreateUserRequest(
			localId!!,
			localName!!,
			platform!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
