package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient

import me.wietlol.reactor.api.Event
import me.wietlol.reactor.core.filteredBy
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.EventMap
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.MessageDeleted
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.MessageEdited
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.MessageMovedIn
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.MessageMovedOut
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.MessagePosted
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.MessageStarred
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.ReplyPosted
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.SeChatEvent
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.UserJoined
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.UserLeft
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.UserMentioned

class Room(
	private val client: SeWebSocketClient,
	private val seEvents: SeChatEvents,
	val roomId: Int
)
{
	val eventMap = EventMap()
	
	val onMessageDeleted: Event<MessageDeleted>
		get() = getEvent()
	val onMessageEdited: Event<MessageEdited>
		get() = getEvent()
	val onMessageMovedIn: Event<MessageMovedIn>
		get() = getEvent()
	val onMessageMovedOut: Event<MessageMovedOut>
		get() = getEvent()
	val onMessagePosted: Event<MessagePosted>
		get() = getEvent()
	val onMessageStarred: Event<MessageStarred>
		get() = getEvent()
	val onReplyPosted: Event<ReplyPosted>
		get() = getEvent()
	val onUserJoined: Event<UserJoined>
		get() = getEvent()
	val onUserLeft: Event<UserLeft>
		get() = getEvent()
	val onUserMentioned: Event<UserMentioned>
		get() = getEvent()
	
	fun leave() = client.leaveRoom(roomId)
	
	fun sendMessage(text: String) = client.sendMessage(roomId, text)
	
	fun editMessage(messageId: Int, text: String) = client.editMessage(messageId, text)
	
	private inline fun <reified E : SeChatEvent> getEvent(): Event<E> =
		eventMap.computeIfAbsent { seEvents.eventMap.get<E>()!!.filteredBy(::isInRoom) }
	
	private fun isInRoom(event: SeChatEvent) =
		event.roomId == roomId
}
