package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient

import me.wietlol.loggo.common.CommonLogger
import me.wietlol.loggo.common.EventId
import me.wietlol.loggo.common.ScopedSourceLogger
import me.wietlol.loggo.common.logError
import me.wietlol.loggo.common.logTrace
import me.wietlol.utils.json.SimpleJsonSerializer
import me.wietlol.utils.json.deserialize
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.SeChatClient
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.UnexpectedSituationException
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.extractFKey
import org.java_websocket.client.WebSocketClient
import java.io.Closeable
import java.net.URI
import java.time.Instant
import java.util.*

class HttpSeWebSocketClient(
	private val chatClient: SeChatClient,
	private val chatSiteUrl: String,
	private val accountFKey: String,
	private val cookieJar: Map<String, String>,
	private val listener: WebSocketListener,
	private val chatEvents: SeChatEvents,
	private val serializer: SimpleJsonSerializer,
	private val initialRoom: Int,
	logger: CommonLogger
) : SeWebSocketClient, SeChatClient by chatClient, Closeable
{
	private val logger = ScopedSourceLogger(logger) { it + "HttpSeWebSocketClient" }
	
	private val reconnectEventId = EventId(75490686, "reconnecting")
	private val closeEventId = EventId(1903508527, "closing")
	private val closeInvokedEventId = EventId(2112234893, "close-invoked")
	private val joinRoomEventId = EventId(1919191373, "join-room")
	private val leaveRoomEventId = EventId(705258753, "leave-room")
	private val leaveAllRoomsEventId = EventId(155768371, "leave-all-rooms")
	private val connectEventId = EventId(471177059, "connecting")
	private val connectErrorEventId = EventId(916289901, "connection-error")
	private val connectedEventId = EventId(1688939054, "connected-successfully")
	
	private val rooms: MutableMap<Int, Room> = HashMap()
	
	private var isClosing = false
	
	private var client: WebSocketClient = connect(initialRoom)
	
	override fun reconnect(roomId: Int)
	{
		logger.logTrace(reconnectEventId, mapOf(
			"roomId" to roomId
		))
		
		runCatching {
			close()
		}
			.onFailure { it.printStackTrace() }
		
		client = connect(roomId)
	}
	
	private fun reconnectIfNotClosing()
	{
		if (isClosing.not())
		{
			logger.logTrace(closeEventId, emptyMap<Any, Any>())
			
			reconnect(initialRoom)
		}
	}
	
	override fun joinRoom(roomId: Int): Room
	{
		logger.logTrace(joinRoomEventId, mapOf(
			"roomId" to roomId
		))
		return khttp.get("$chatSiteUrl/rooms/$roomId")
			.apply {
				if (statusCode == 404)
					throw RoomNotFoundException("Room $roomId does not exist.")
			}
			.apply { text.extractFKey() }
			.run { getRoom(roomId) }
			.also { reconnect(it.roomId) }
	}
	
	override fun getRoom(roomId: Int): Room =
		rooms.computeIfAbsent(roomId) { Room(this, chatEvents, it) }
	
	override fun leaveRoom(roomId: Int)
	{
		logger.logTrace(leaveRoomEventId, mapOf(
			"roomId" to roomId
		))
		khttp.post(
			"$chatSiteUrl/chats/leave/$roomId",
			data = mapOf(
				"quiet" to "true",
				"fkey" to accountFKey
			),
			cookies = cookieJar
		)
			.apply {
				if (statusCode == 404)
					throw RoomNotFoundException("Room $roomId does not exist.")
			}
			.also { reconnect(initialRoom) }
	}
	
	override fun leaveAllRooms()
	{
		logger.logTrace(leaveAllRoomsEventId, emptyMap<Any, Any>())
		khttp.post(
			"$chatSiteUrl/chats/leave/all",
			data = mapOf(
				"quiet" to "true",
				"fkey" to accountFKey
			),
			cookies = cookieJar
		)
	}
	
	override fun close()
	{
		logger.logTrace(closeInvokedEventId, emptyMap<Any, Any>())
		
		isClosing = true
		
		client.close()
	}
	
	private data class WsAuthResponse(val url: String)
	
	private fun connect(roomId: Int): WebSocketClient
	{
		logger.logTrace(connectEventId, mapOf(
			"roomId" to roomId,
			"url" to "$chatSiteUrl/ws-auth"
		))
		
		try
		{
			val wssResponse: WsAuthResponse = khttp.post(
				"$chatSiteUrl/ws-auth",
				cookies = cookieJar,
				data = mapOf(
					"roomId" to roomId.toString(),
					"fkey" to accountFKey
				),
				headers = mapOf(
					"Origin" to chatSiteUrl,
					"Referer" to "$chatSiteUrl/rooms/$roomId",
					"Content-Type" to "application/x-www-form-urlencoded"
				)
			)
				.also {
					if (it.statusCode == 404)
						throw UnexpectedSituationException("ws-auth not found")
				}
				.let { serializer.deserialize(it.text) }
			
			val chatSiteUrl = "https://chat.stackoverflow.com"
			val webSocketUrl = "${wssResponse.url}?l=${Instant.now().toEpochMilli()}"
			
			val listener = listener.let {
				ReconnectingWebSocketListener(it) { reconnectIfNotClosing() }
			}
			
			return JavaWebSocketClientAdapter(URI(webSocketUrl), mapOf("Origin" to chatSiteUrl), listener)
				.apply { connect() }
				.also {
					logger.logTrace(connectedEventId, emptyMap<Any, Any>())
				}
		}
		catch (ex: Throwable)
		{
			logger.logError(connectErrorEventId, mapOf(
				"roomId" to roomId
			), ex)
			
			throw ex
		}
	}
}
