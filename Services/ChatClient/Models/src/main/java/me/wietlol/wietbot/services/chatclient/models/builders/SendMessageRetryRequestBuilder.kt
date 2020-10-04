// hash: #35370a07
// @formatter:off
package me.wietlol.wietbot.services.chatclient.models.builders

import me.wietlol.wietbot.services.chatclient.models.models.*
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRetryRequestImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class SendMessageRetryRequestBuilder
{
	var request: SendMessageRequest?
		= null
	
	var tryCount: Int?
		= null
	
	fun build(): SendMessageRetryRequest =
		SendMessageRetryRequestImpl(
			request!!,
			tryCount!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
