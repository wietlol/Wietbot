// hash: #8282e5fd
// @formatter:off
package me.wietlol.wietbot.services.chatclient.models.builders

import me.wietlol.wietbot.data.messages.models.models.Content
import me.wietlol.wietbot.services.chatclient.models.models.*
import me.wietlol.wietbot.services.chatclient.models.models.EditMessageRequestImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class EditMessageRequestBuilder
{
	var platform: String?
		= null
	
	var messageId: String?
		= null
	
	var content: Content?
		= null
	
	fun build(): EditMessageRequest =
		EditMessageRequestImpl(
			platform!!,
			messageId!!,
			content!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
