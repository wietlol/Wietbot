// hash: #fd2ba6b3
// @formatter:off
package me.wietlol.wietbot.data.commands.models.builders

import me.wietlol.wietbot.data.commands.models.models.*
import me.wietlol.wietbot.data.commands.models.models.CreateCommandRequestImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class CreateCommandRequestBuilder
{
	var keyword: String?
		= null
	
	var listenerQueue: String?
		= null
	
	fun build(): CreateCommandRequest =
		CreateCommandRequestImpl(
			keyword!!,
			listenerQueue!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
