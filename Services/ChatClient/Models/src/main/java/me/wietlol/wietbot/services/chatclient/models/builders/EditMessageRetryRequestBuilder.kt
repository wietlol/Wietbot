package me.wietlol.wietbot.services.chatclient.models.builders

import me.wietlol.wietbot.services.chatclient.models.models.*
import java.util.*

class EditMessageRetryRequestBuilder
{
	var request: EditMessageRequest? = null
	var tryCount: Int? = null
	
	fun build(): EditMessageRetryRequest =
		EditMessageRetryRequest.of(
			request!!,
			tryCount!!
		)
}
