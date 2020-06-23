package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient

import me.wietlol.serialization.JsonSerializer2
import me.wietlol.serialization.deserialize
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
	private val serializer: JsonSerializer2,
	private val initialRoom: Int = 1
) : SeWebSocketClient, SeChatClient by chatClient, Closeable
{
	private val rooms: MutableMap<Int, Room> = HashMap()
	
	private var isClosing = false
	
	private var client: WebSocketClient = connect(initialRoom)
	
	override fun reconnect(roomId: Int)
	{
		runCatching {
			close()
		}
			.onFailure { it.printStackTrace() }
		
		client = connect(roomId)
	}
	
	private fun reconnectIfNotClosing()
	{
		if (isClosing.not())
			reconnect(initialRoom)
	}
	
	override fun joinRoom(roomId: Int): Room =
		khttp.get("$chatSiteUrl/rooms/$roomId")
			.apply {
				if (statusCode == 404)
					throw RoomNotFoundException("Room $roomId does not exist.")
			}
			.apply { text.extractFKey() }
			.run { getRoom(roomId) }
			.also { reconnect(it.roomId) }
	
	override fun getRoom(roomId: Int): Room =
		rooms.computeIfAbsent(roomId) { Room(this, chatEvents, it) }
	
	override fun leaveRoom(roomId: Int)
	{
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
		isClosing = true
		
		client.close()
	}
	
	private data class WsAuthResponse(val url: String)
	private fun connect(roomId: Int): WebSocketClient
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
	}
}
