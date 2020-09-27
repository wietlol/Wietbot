// hash: #7937d293
// @formatter:off
package me.wietlol.wietbot.data.commands.models.builders

import me.wietlol.wietbot.data.commands.models.models.*
import me.wietlol.wietbot.data.commands.models.models.ListCommandsResponseImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class ListCommandsResponseBuilder
{
	var commands: MutableList<Command>?
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
