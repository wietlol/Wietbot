// hash: #7685d951
// @formatter:off
package me.wietlol.wietbot.data.commands.models.builders

import me.wietlol.wietbot.data.commands.models.models.*
import me.wietlol.wietbot.data.commands.models.models.CommandImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class CommandBuilder
{
	var keyword: String?
		= null
	
	var listenerQueue: String?
		= null
	
	fun build(): Command =
		CommandImpl(
			keyword!!,
			listenerQueue!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
