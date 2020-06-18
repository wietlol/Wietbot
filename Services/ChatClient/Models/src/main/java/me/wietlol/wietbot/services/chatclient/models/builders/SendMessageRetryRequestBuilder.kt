package me.wietlol.wietbot.services.chatclient.models.builders

import me.wietlol.wietbot.services.chatclient.models.models.*
import java.util.*

class SendMessageRetryRequestBuilder
{
	var request: SendMessageRequest? = null
	var tryCount: Int? = null
	
	fun build(): SendMessageRetryRequest =
		SendMessageRetryRequest.of(
			request!!,
			tryCount!!
		)
}
