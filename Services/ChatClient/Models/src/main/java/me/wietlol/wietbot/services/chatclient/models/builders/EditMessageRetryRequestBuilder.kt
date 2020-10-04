// hash: #7b32033d
// @formatter:off
package me.wietlol.wietbot.services.chatclient.models.builders

import me.wietlol.wietbot.services.chatclient.models.models.*
import me.wietlol.wietbot.services.chatclient.models.models.EditMessageRetryRequestImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class EditMessageRetryRequestBuilder
{
	var request: EditMessageRequest?
		= null
	
	var tryCount: Int?
		= null
	
	fun build(): EditMessageRetryRequest =
		EditMessageRetryRequestImpl(
			request!!,
			tryCount!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
