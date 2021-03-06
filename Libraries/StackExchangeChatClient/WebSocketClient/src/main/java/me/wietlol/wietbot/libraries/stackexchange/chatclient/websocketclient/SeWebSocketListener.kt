package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient

import me.wietlol.utils.json.SimpleJsonSerializer
import me.wietlol.utils.json.deserialize
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.BulkChatEvent
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.EventMap

class SeWebSocketListener(
	val webSocketListener: WebSocketListener,
	val serializer: SimpleJsonSerializer,
	val eventMap: EventMap
) : WebSocketListener by webSocketListener
{
	override fun onMessage(message: String)
	{
		val bulkChatEvent = serializer.deserialize<BulkChatEvent>(message)
		
		bulkChatEvent
			.events
			.forEach { eventMap.get(it.javaClass)?.execute(it) }
		
		webSocketListener.onMessage(message)
	}
}
