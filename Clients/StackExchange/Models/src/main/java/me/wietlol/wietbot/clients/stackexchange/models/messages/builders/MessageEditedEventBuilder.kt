// hash: #8f3ff27a
// @formatter:off
package me.wietlol.wietbot.clients.stackexchange.models.messages.builders

import me.wietlol.wietbot.clients.stackexchange.models.messages.models.*
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.MessageEditedEventImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class MessageEditedEventBuilder
{
	var id: Int?
		= null
	
	var timeStamp: Long?
		= null
	
	var messageId: Int?
		= null
	
	var content: String?
		= null
	
	var messageEdits: Int?
		= null
	
	var user: User?
		= null
	
	var room: Room?
		= null
	
	fun build(): MessageEditedEvent =
		MessageEditedEventImpl(
			id!!,
			timeStamp!!,
			messageId!!,
			content!!,
			messageEdits!!,
			user!!,
			room!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
