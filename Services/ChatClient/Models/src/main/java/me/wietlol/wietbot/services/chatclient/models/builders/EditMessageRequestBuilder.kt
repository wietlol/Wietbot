// hash: #edbd48f6
// @formatter:off
package me.wietlol.wietbot.services.chatclient.models.builders

import me.wietlol.wietbot.services.chatclient.models.models.*
import me.wietlol.wietbot.services.chatclient.models.models.EditMessageRequestImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class EditMessageRequestBuilder
{
	var messageId: Int?
		= null
	
	var text: String?
		= null
	
	fun build(): EditMessageRequest =
		EditMessageRequestImpl(
			messageId!!,
			text!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
