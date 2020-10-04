package me.wietlol.wietbot.services.chatclient.core.interfaces

import me.wietlol.wietbot.services.chatclient.models.models.EditMessageRequest
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequest

interface Platform
{
	fun sendMessage(request: SendMessageRequest)
	
	fun editMessage(request: EditMessageRequest)
}
