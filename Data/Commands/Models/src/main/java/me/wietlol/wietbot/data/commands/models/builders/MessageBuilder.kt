// hash: #343e5322
// @formatter:off
package me.wietlol.wietbot.data.commands.models.builders

import me.wietlol.wietbot.data.commands.models.models.*
import me.wietlol.wietbot.data.commands.models.models.MessageImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class MessageBuilder
{
	var id: Int?
		= null
	
	var sender: ChatUser?
		= null
	
	var fullText: String?
		= null
	
	var source: MessageSource?
		= null
	
	fun build(): Message =
		MessageImpl(
			id!!,
			sender!!,
			fullText!!,
			source!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
