package me.wietlol.wietbot.services.chatclient.core.services

import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.SeChatClient
import me.wietlol.wietbot.services.chatclient.core.interfaces.Platform
import me.wietlol.wietbot.services.chatclient.models.models.EditMessageRequest
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequest

class StackOverflowPlatform(
	val chatClient: SeChatClient
) : Platform
{
	private val formatter = StackOverflowMessageFormatter()
	
	override fun sendMessage(request: SendMessageRequest)
	{
		chatClient.sendMessage(request.target.toInt(), formatter.format(request.content))
	}
	
	override fun editMessage(request: EditMessageRequest)
	{
		chatClient.editMessage(request.messageId.toInt(), formatter.format(request.content))
	}
}
