package me.wietlol.wietbot.services.chatclient.models.builders

import me.wietlol.wietbot.services.chatclient.models.models.*
import java.util.*

class SendMessageResponseBuilder
{
	var id: Int? = null
	
	fun build(): SendMessageResponse =
		SendMessageResponse.of(
			id!!
		)
}
