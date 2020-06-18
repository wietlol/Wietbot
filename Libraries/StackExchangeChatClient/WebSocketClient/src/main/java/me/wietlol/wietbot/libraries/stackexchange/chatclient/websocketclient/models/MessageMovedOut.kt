package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.EventType.MessageMovedOut

@JsonIgnoreProperties(ignoreUnknown = true)
data class MessageMovedOut(
	override val roomId: Int,
	override val roomName: String,
	override val id: Int
) : SeChatEvent
{
	override val eventType: EventType
		get() = MessageMovedOut
}
