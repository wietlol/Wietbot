package me.wietlol.wietbot.services.chatclient.models.builders

import me.wietlol.wietbot.services.chatclient.models.models.*
import java.util.*

class EditMessageRequestBuilder
{
	var messageId: Int? = null
	var text: String? = null
	
	fun build(): EditMessageRequest =
		EditMessageRequest.of(
			messageId!!,
			text!!
		)
}
