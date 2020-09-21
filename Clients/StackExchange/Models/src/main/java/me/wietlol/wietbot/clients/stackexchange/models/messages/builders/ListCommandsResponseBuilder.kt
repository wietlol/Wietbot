// hash: #fb64ec66
// @formatter:off
package me.wietlol.wietbot.clients.stackexchange.models.messages.builders

import me.wietlol.wietbot.clients.stackexchange.models.messages.models.*
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.ListCommandsResponseImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class ListCommandsResponseBuilder
{
	var commands: MutableList<String>?
		= mutableListOf()
	
	fun build(): ListCommandsResponse =
		ListCommandsResponseImpl(
			commands!!.toMutableList(),
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
