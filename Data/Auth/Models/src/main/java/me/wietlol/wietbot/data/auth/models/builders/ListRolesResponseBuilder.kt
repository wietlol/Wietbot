// hash: #013bf7d0
// @formatter:off
package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.ListRolesResponseImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class ListRolesResponseBuilder
{
	var roles: MutableList<Role>?
		= mutableListOf()
	
	fun build(): ListRolesResponse =
		ListRolesResponseImpl(
			roles!!.toMutableList(),
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
