package me.wietlol.wietbot.services.chatclient.models

import me.wietlol.wietbot.services.chatclient.models.models.EditMessageRequest
import me.wietlol.wietbot.services.chatclient.models.models.EditMessageResponse
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequest
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageResponse

interface ChatClientService
{
	fun sendMessage(request: SendMessageRequest): SendMessageResponse
	
	fun editMessage(request: EditMessageRequest): EditMessageResponse
}
