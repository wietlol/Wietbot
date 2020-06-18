package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient

class ReconnectingWebSocketListener(
	val listener: WebSocketListener,
	val reconnect: () -> Unit
) : WebSocketListener by listener
{
	override fun onClose(code: Int, reason: String, remote: Boolean)
	{
		listener.onClose(code, reason, remote)
		reconnect()
	}
}
