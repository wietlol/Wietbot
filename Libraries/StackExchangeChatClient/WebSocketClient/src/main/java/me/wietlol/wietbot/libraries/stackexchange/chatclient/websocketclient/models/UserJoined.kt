package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.EventType.UserJoined

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserJoined(
	val timeStamp: Long,
	override val id: Int,
	val userId: Int,
	val targetUserId: Int,
	val userName: String,
	override val roomId: Int,
	override val roomName: String
) : SeChatEvent
{
	override val eventType: EventType
		get() = UserJoined
}
