package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.EventType.UserMentioned

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserMentioned(
	val timeStamp: Long,
	val content: String,
	override val id: Int,
	val userId: Int,
	val targetUserId: Int,
	val userName: String,
	override val roomId: Int,
	override val roomName: String,
	val messageId: Int,
	val parentId: Int?,
	val showParent: Boolean?
) : SeChatEvent
{
	override val eventType: EventType
		get() = UserMentioned
}
