package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient

import org.java_websocket.handshake.ServerHandshake
import java.net.URI

class JavaWebSocketClientAdapter(
	serverUri: URI?,
	httpHeaders: Map<String, String>,
	val listener: WebSocketListener
) : org.java_websocket.client.WebSocketClient(serverUri, httpHeaders)
{
	init
	{
		// https://github.com/TooTallNate/Java-WebSocket/wiki/Lost-connection-detection
		// connection checking disabled because it behaves weird for SO-Chat connections
		connectionLostTimeout = 0
	}
	
	override fun onOpen(handshakedata: ServerHandshake?)
	{
		// unused
	}
	
	override fun onError(ex: Exception?)
	{
		// unused
	}
	
	override fun onMessage(message: String)
	{
		listener.onMessage(message)
	}
	
	override fun onClose(code: Int, reason: String, remote: Boolean)
	{
		listener.onClose(code, reason, remote)
	}
}
