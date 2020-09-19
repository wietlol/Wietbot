package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.EventType.MessageStarred

@JsonIgnoreProperties(ignoreUnknown = true)
data class MessageStarred(
	val timeStamp: Long,
	val content: String,
	override val id: Int,
	override val roomId: Int,
	override val roomName: String,
	val messageId: Int,
	val messageStars: Int
) : SeChatEvent
{
	override val eventType: EventType
		get() = MessageStarred
}
