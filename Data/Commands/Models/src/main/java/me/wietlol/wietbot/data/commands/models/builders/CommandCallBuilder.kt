// hash: #e209c184
// @formatter:off
package me.wietlol.wietbot.data.commands.models.builders

import me.wietlol.wietbot.data.commands.models.models.*
import me.wietlol.wietbot.data.commands.models.models.CommandCallImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class CommandCallBuilder
{
	var commandKeyword: String?
		= null
	
	var argumentText: String?
		= null
	
	var message: Message?
		= null
	
	var options: MutableMap<String, String>?
		= null
	
	fun build(): CommandCall =
		CommandCallImpl(
			commandKeyword!!,
			argumentText!!,
			message!!,
			options!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
