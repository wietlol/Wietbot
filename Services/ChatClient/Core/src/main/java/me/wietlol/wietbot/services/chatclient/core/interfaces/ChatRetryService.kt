package me.wietlol.wietbot.services.chatclient.core.interfaces

import me.wietlol.wietbot.services.chatclient.models.models.EditMessageRequest
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequest

interface ChatRetryService
{
	fun retrySendMessage(request: SendMessageRequest, tryCount: Int)
	
	fun retryEditMessage(request: EditMessageRequest, tryCount: Int)
}
