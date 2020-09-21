// hash: #44f1216a
// @formatter:off
package me.wietlol.wietbot.clients.stackexchange.models.messages.builders

import me.wietlol.wietbot.clients.stackexchange.models.messages.models.*
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.MessagePostedEventImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class MessagePostedEventBuilder
{
	var id: Int?
		= null
	
	var timeStamp: Long?
		= null
	
	var messageId: Int?
		= null
	
	var content: String?
		= null
	
	var parentId: Int?
		= null
	
	var showParent: Boolean?
		= null
	
	var user: User?
		= null
	
	var room: Room?
		= null
	
	fun build(): MessagePostedEvent =
		MessagePostedEventImpl(
			id!!,
			timeStamp!!,
			messageId!!,
			content!!,
			parentId,
			showParent,
			user!!,
			room!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
