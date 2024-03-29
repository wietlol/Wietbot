// hash: #d8d7cd0d
// @formatter:off
package me.wietlol.wietbot.services.chatclient.models.builders

import me.wietlol.wietbot.data.messages.models.models.Content
import me.wietlol.wietbot.services.chatclient.models.models.*
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequestImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class SendMessageRequestBuilder
{
	var platform: String?
		= null
	
	var target: String?
		= null
	
	var content: Content?
		= null
	
	fun build(): SendMessageRequest =
		SendMessageRequestImpl(
			platform!!,
			target!!,
			content!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
