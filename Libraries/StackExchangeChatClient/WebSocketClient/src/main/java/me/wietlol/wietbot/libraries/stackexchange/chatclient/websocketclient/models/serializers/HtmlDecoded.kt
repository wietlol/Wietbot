package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.serializers

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
import org.jsoup.Jsoup

fun SeChatEvent.htmlDecode(): SeChatEvent =
	when (this)
	{
		is MessageDeleted -> htmlDecode()
		is MessageEdited -> htmlDecode()
		is MessageMovedIn -> htmlDecode()
		is MessageMovedOut -> htmlDecode()
		is MessagePosted -> htmlDecode()
		is MessageStarred -> htmlDecode()
		is ReplyPosted -> htmlDecode()
		is UserJoined -> htmlDecode()
		is UserLeft -> htmlDecode()
		is UserMentioned -> htmlDecode()
		else -> this
	}

fun MessageDeleted.htmlDecode(): MessageDeleted =
	MessageDeleted(
		timeStamp,
		id,
		userId,
		userName.htmlDecode(),
		roomId,
		roomName.htmlDecode(),
		messageId,
		messageEdits
	)

fun MessageEdited.htmlDecode(): MessageEdited =
	MessageEdited(
		timeStamp,
		content.htmlDecode(),
		id,
		userId,
		userName.htmlDecode(),
		roomId,
		roomName.htmlDecode(),
		messageId,
		messageEdits
	)

fun MessageMovedIn.htmlDecode(): MessageMovedIn =
	MessageMovedIn(
		roomId,
		roomName,
		id
	)

fun MessageMovedOut.htmlDecode(): MessageMovedOut =
	MessageMovedOut(
		roomId,
		roomName,
		id
	)

fun MessagePosted.htmlDecode(): MessagePosted =
	MessagePosted(
		timeStamp,
		content.htmlDecode(),
		id,
		userId,
		userName.htmlDecode(),
		roomId,
		roomName.htmlDecode(),
		messageId,
		parentId,
		showParent
	)

fun MessageStarred.htmlDecode(): MessageStarred =
	MessageStarred(
		timeStamp,
		content.htmlDecode(),
		id,
		roomId,
		roomName.htmlDecode(),
		messageId,
		messageStars
	)

fun ReplyPosted.htmlDecode(): ReplyPosted =
	ReplyPosted(
		timeStamp,
		content.htmlDecode(),
		id,
		userId,
		targetUserId,
		userName.htmlDecode(),
		roomId,
		roomName.htmlDecode(),
		messageId,
		parentId,
		showParent
	)

fun UserJoined.htmlDecode(): UserJoined =
	UserJoined(
		timeStamp,
		id,
		userId,
		targetUserId,
		userName.htmlDecode(),
		roomId,
		roomName.htmlDecode()
	)

fun UserLeft.htmlDecode(): UserLeft =
	UserLeft(
		timeStamp,
		id,
		userId,
		targetUserId,
		userName.htmlDecode(),
		roomId,
		roomName.htmlDecode()
	)

fun UserMentioned.htmlDecode(): UserMentioned =
	UserMentioned(
		timeStamp,
		content.htmlDecode(),
		id,
		userId,
		targetUserId,
		userName.htmlDecode(),
		roomId,
		roomName.htmlDecode(),
		messageId,
		parentId,
		showParent
	)

private fun String.htmlDecode(): String =
	Jsoup.parse(this).text()
