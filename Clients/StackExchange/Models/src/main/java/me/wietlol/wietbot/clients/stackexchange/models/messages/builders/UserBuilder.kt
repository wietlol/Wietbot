// hash: #82ec91aa
// @formatter:off
package me.wietlol.wietbot.clients.stackexchange.models.messages.builders

import me.wietlol.wietbot.clients.stackexchange.models.messages.models.*
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.UserImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class UserBuilder
{
	var id: Int?
		= null
	
	var name: String?
		= null
	
	fun build(): User =
		UserImpl(
			id!!,
			name!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
