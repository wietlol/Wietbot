package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient

import me.wietlol.reactor.ExecutableEvent
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.EventMap
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.MessageDeleted
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.MessageEdited
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.MessageMovedIn
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.MessageMovedOut
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.MessagePosted
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.MessageStarred
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.ReplyPosted
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.UserJoined
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.UserLeft
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.UserMentioned

interface SeChatEvents
{
	val onMessageDeleted: ExecutableEvent<MessageDeleted>
	val onMessageEdited: ExecutableEvent<MessageEdited>
	val onMessageMovedIn: ExecutableEvent<MessageMovedIn>
	val onMessageMovedOut: ExecutableEvent<MessageMovedOut>
	val onMessagePosted: ExecutableEvent<MessagePosted>
	val onMessageStarred: ExecutableEvent<MessageStarred>
	val onReplyPosted: ExecutableEvent<ReplyPosted>
	val onUserJoined: ExecutableEvent<UserJoined>
	val onUserLeft: ExecutableEvent<UserLeft>
	val onUserMentioned: ExecutableEvent<UserMentioned>
	
	val eventMap: EventMap
}
