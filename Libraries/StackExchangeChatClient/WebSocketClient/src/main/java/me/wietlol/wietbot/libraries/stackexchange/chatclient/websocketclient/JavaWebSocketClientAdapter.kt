package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient

import me.wietlol.loggo.common.CommonLogger
import me.wietlol.loggo.common.EventId
import me.wietlol.loggo.common.ScopedSourceLogger
import me.wietlol.loggo.common.logInformation
import org.java_websocket.WebSocket
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft
import org.java_websocket.framing.Framedata
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.handshake.ServerHandshake
import org.java_websocket.handshake.ServerHandshakeBuilder
import java.net.URI
import java.nio.ByteBuffer

class JavaWebSocketClientAdapter(
	serverUri: URI?,
	httpHeaders: Map<String, String>,
	val listener: WebSocketListener,
	logger: CommonLogger,
) : WebSocketClient(serverUri, httpHeaders)
{
	private val logger = ScopedSourceLogger(logger) { it + "JavaWebSocketClientAdapter" }
	
	init
	{
		// https://github.com/TooTallNate/Java-WebSocket/wiki/Lost-connection-detection
		// connection checking disabled because it behaves weird for SO-Chat connections
		connectionLostTimeout = 0
	}
	
	private val onOpenEventId = EventId(1778280752, "onOpen-invoke")
	private val onErrorEventId = EventId(1432886368, "onError-invoke")
	private val onMessageEventId = EventId(1314582054, "onMessage-invoke")
	private val onCloseEventId = EventId(1899387131, "onClose-invoke")
	private val onMessage2EventId = EventId(1255914631, "onMessage-invoke")
	private val onWebsocketHandshakeReceivedAsServerEventId = EventId(1782058773, "onWebsocketHandshakeReceivedAsServer-invoke")
	private val onWebsocketHandshakeReceivedAsClientEventId = EventId(1659378048, "onWebsocketHandshakeReceivedAsClient-invoke")
	private val onWebsocketHandshakeSentAsClientEventId = EventId(1318810130, "onWebsocketHandshakeSentAsClient-invoke")
	private val onWebsocketClosingEventId = EventId(1324957414, "onWebsocketClosing-invoke")
	private val onWebsocketCloseInitiatedEventId = EventId(2056186421, "onWebsocketCloseInitiated-invoke")
	private val onWebsocketPingEventId = EventId(1505894333, "onWebsocketPing-invoke")
	private val onWebsocketPongEventId = EventId(1935853505, "onWebsocketPong-invoke")
	private val onCloseInitiatedEventId = EventId(1791856429, "onCloseInitiated-invoke")
	private val onClosingEventId = EventId(1807640091, "onClosing-invoke")
	
	override fun onOpen(handshakedata: ServerHandshake?)
	{
		logger.logInformation(onOpenEventId, mapOf(
			"handshakedata.httpStatus" to handshakedata?.httpStatus,
			"handshakedata.httpStatusMessage" to handshakedata?.httpStatusMessage,
		))
		// unused
	}
	
	override fun onError(ex: Exception?)
	{
		logger.logInformation(onErrorEventId, mapOf<Any, Any>(), ex)
		// unused
	}
	
	override fun onMessage(message: String)
	{
		logger.logInformation(onMessageEventId, mapOf(
			"message" to message,
		))
		listener.onMessage(message)
	}
	
	override fun onClose(code: Int, reason: String, remote: Boolean)
	{
		logger.logInformation(onCloseEventId, mapOf(
			"code" to code,
			"reason" to reason,
			"remote" to remote,
		))
		listener.onClose(code, reason, remote)
	}
	
	override fun onMessage(bytes: ByteBuffer?)
	{
		logger.logInformation(onMessage2EventId, mapOf<Any, Any>())
		super.onMessage(bytes)
	}
	
	override fun onWebsocketHandshakeReceivedAsServer(conn: WebSocket?, draft: Draft?, request: ClientHandshake?): ServerHandshakeBuilder
	{
		logger.logInformation(onWebsocketHandshakeReceivedAsServerEventId, mapOf(
			"request.resourceDescriptor" to request?.resourceDescriptor,
		))
		return super.onWebsocketHandshakeReceivedAsServer(conn, draft, request)
	}
	
	override fun onWebsocketHandshakeReceivedAsClient(conn: WebSocket?, request: ClientHandshake?, response: ServerHandshake?)
	{
		logger.logInformation(onWebsocketHandshakeReceivedAsClientEventId, mapOf(
			"request.resourceDescriptor" to request?.resourceDescriptor,
			"response.httpStatus" to response?.httpStatus,
			"response.httpStatusMessage" to response?.httpStatusMessage,
		))
		super.onWebsocketHandshakeReceivedAsClient(conn, request, response)
	}
	
	override fun onWebsocketHandshakeSentAsClient(conn: WebSocket?, request: ClientHandshake?)
	{
		logger.logInformation(onWebsocketHandshakeSentAsClientEventId, mapOf(
			"request.resourceDescriptor" to request?.resourceDescriptor,
		))
		super.onWebsocketHandshakeSentAsClient(conn, request)
	}
	
	override fun onWebsocketClosing(conn: WebSocket?, code: Int, reason: String?, remote: Boolean)
	{
		logger.logInformation(onWebsocketClosingEventId, mapOf(
			"code" to code,
			"reason" to reason,
			"remote" to remote,
		))
		super.onWebsocketClosing(conn, code, reason, remote)
	}
	
	override fun onWebsocketCloseInitiated(conn: WebSocket?, code: Int, reason: String?)
	{
		logger.logInformation(onWebsocketCloseInitiatedEventId, mapOf(
			"code" to code,
			"reason" to reason,
		))
		super.onWebsocketCloseInitiated(conn, code, reason)
	}
	
	override fun onWebsocketPing(conn: WebSocket?, f: Framedata?)
	{
		logger.logInformation(onWebsocketPingEventId, mapOf<Any, Any>())
		super.onWebsocketPing(conn, f)
	}
	
	override fun onWebsocketPong(conn: WebSocket?, f: Framedata?)
	{
		logger.logInformation(onWebsocketPongEventId, mapOf<Any, Any>())
		super.onWebsocketPong(conn, f)
	}
	
	override fun onCloseInitiated(code: Int, reason: String?)
	{
		logger.logInformation(onCloseInitiatedEventId, mapOf(
			"code" to code,
			"reason" to reason,
		))
		super.onCloseInitiated(code, reason)
	}
	
	override fun onClosing(code: Int, reason: String?, remote: Boolean)
	{
		logger.logInformation(onClosingEventId, mapOf(
			"code" to code,
			"reason" to reason,
			"remote" to remote,
		))
		super.onClosing(code, reason, remote)
	}
}
