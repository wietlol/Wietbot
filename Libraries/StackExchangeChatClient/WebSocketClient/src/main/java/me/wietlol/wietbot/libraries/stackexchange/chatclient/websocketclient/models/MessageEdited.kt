package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.EventType.MessageEdited

@JsonIgnoreProperties(ignoreUnknown = true)
data class MessageEdited(
	val timeStamp: Int,
	override val content: String,
	override val id: Int,
	override val userId: Int,
	override val userName: String,
	override val roomId: Int,
	override val roomName: String,
	val messageId: Int,
	val messageEdits: Int
) : MessageEvent
{
	override val eventType: EventType
		get() = MessageEdited
}
