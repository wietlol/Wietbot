package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient

import me.wietlol.reactor.ExecutableEvent
import me.wietlol.reactor.asSwallowed
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

class CommonSeChatEvents : SeChatEvents
{
	override val onMessageDeleted: ExecutableEvent<MessageDeleted> = ExecutableEvent.create<MessageDeleted>().asSwallowed()
	override val onMessageEdited: ExecutableEvent<MessageEdited> = ExecutableEvent.create<MessageEdited>().asSwallowed()
	override val onMessageMovedIn: ExecutableEvent<MessageMovedIn> = ExecutableEvent.create<MessageMovedIn>().asSwallowed()
	override val onMessageMovedOut: ExecutableEvent<MessageMovedOut> = ExecutableEvent.create<MessageMovedOut>().asSwallowed()
	override val onMessagePosted: ExecutableEvent<MessagePosted> = ExecutableEvent.create<MessagePosted>().asSwallowed()
	override val onMessageStarred: ExecutableEvent<MessageStarred> = ExecutableEvent.create<MessageStarred>().asSwallowed()
	override val onReplyPosted: ExecutableEvent<ReplyPosted> = ExecutableEvent.create<ReplyPosted>().asSwallowed()
	override val onUserJoined: ExecutableEvent<UserJoined> = ExecutableEvent.create<UserJoined>().asSwallowed()
	override val onUserLeft: ExecutableEvent<UserLeft> = ExecutableEvent.create<UserLeft>().asSwallowed()
	override val onUserMentioned: ExecutableEvent<UserMentioned> = ExecutableEvent.create<UserMentioned>().asSwallowed()
	
	override val eventMap = EventMap()
		.also { it.add(onMessageDeleted) }
		.also { it.add(onMessageEdited) }
		.also { it.add(onMessageMovedIn) }
		.also { it.add(onMessageMovedOut) }
		.also { it.add(onMessagePosted) }
		.also { it.add(onMessageStarred) }
		.also { it.add(onReplyPosted) }
		.also { it.add(onUserJoined) }
		.also { it.add(onUserLeft) }
		.also { it.add(onUserMentioned) }
}
