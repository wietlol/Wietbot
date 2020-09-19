package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.EventType.MessageDeleted

@JsonIgnoreProperties(ignoreUnknown = true)
data class MessageDeleted(
	val timeStamp: Long,
	override val id: Int,
	val userId: Int,
	val userName: String,
	override val roomId: Int,
	override val roomName: String,
	val messageId: Int,
	val messageEdits: Int
) : SeChatEvent
{
	override val eventType: EventType
		get() = MessageDeleted
}
