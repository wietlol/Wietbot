// hash: #760ae879
// @formatter:off
package me.wietlol.wietbot.clients.stackexchange.models.messages.builders

import me.wietlol.wietbot.clients.stackexchange.models.messages.models.*
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.MessageDeletedEventImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class MessageDeletedEventBuilder
{
	var id: Int?
		= null
	
	var timeStamp: Long?
		= null
	
	var messageId: Int?
		= null
	
	var messageEdits: Int?
		= null
	
	var user: User?
		= null
	
	var room: Room?
		= null
	
	fun build(): MessageDeletedEvent =
		MessageDeletedEventImpl(
			id!!,
			timeStamp!!,
			messageId!!,
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
