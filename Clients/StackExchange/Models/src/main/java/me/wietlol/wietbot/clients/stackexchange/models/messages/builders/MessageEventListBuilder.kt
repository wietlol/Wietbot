// hash: #e87b89b1
// @formatter:off
package me.wietlol.wietbot.clients.stackexchange.models.messages.builders

import me.wietlol.wietbot.clients.stackexchange.models.messages.models.*
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.MessageEventListImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class MessageEventListBuilder
{
	var messageId: Int?
		= null
	
	var events: MutableList<MessageEvent>?
		= mutableListOf()
	
	fun build(): MessageEventList =
		MessageEventListImpl(
			messageId!!,
			events!!.toMutableList(),
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
