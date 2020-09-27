// hash: #b2f8913a
// @formatter:off
package me.wietlol.wietbot.data.commands.models.builders

import me.wietlol.wietbot.data.commands.models.models.*
import me.wietlol.wietbot.data.commands.models.models.RemoveCommandRequestImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class RemoveCommandRequestBuilder
{
	var keyword: String?
		= null
	
	fun build(): RemoveCommandRequest =
		RemoveCommandRequestImpl(
			keyword!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
