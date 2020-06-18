package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient

interface WebSocketListener
{
	fun onMessage(message: String)
	
	fun onClose(code: Int, reason: String, remote: Boolean)
}
