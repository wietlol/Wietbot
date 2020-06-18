package me.wietlol.wietbot.clients.stackexchange.util

import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.WebSocketListener

class LoggingWebSocketListener(
	val webSocketListener: WebSocketListener,
	val action: (String) -> Unit
) : WebSocketListener
{
	override fun onClose(code: Int, reason: String, remote: Boolean)
	{
		webSocketListener.onClose(code, reason, remote)
	}
	
	override fun onMessage(message: String)
	{
		action(message)
		webSocketListener.onMessage(message)
	}
}
