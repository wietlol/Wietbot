package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient

class NoOpWebSocketListener : WebSocketListener
{
	override fun onMessage(message: String)
	{
		// nothing to do
	}
	
	override fun onClose(code: Int, reason: String, remote: Boolean)
	{
		// nothing to do
	}
}
