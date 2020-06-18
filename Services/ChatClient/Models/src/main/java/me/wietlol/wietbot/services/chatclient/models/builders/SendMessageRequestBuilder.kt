package me.wietlol.wietbot.services.chatclient.models.builders

import me.wietlol.wietbot.services.chatclient.models.models.*
import java.util.*

class SendMessageRequestBuilder
{
	var roomId: Int? = null
	var text: String? = null
	
	fun build(): SendMessageRequest =
		SendMessageRequest.of(
			roomId!!,
			text!!
		)
}
