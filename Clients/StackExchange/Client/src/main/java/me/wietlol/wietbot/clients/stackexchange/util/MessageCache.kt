package me.wietlol.wietbot.clients.stackexchange.util

import me.wietlol.wietbot.data.messages.models.models.ChatEvent
import me.wietlol.wietbot.data.messages.models.models.ChatUserImpl
import me.wietlol.wietbot.data.messages.models.models.MessageDeletedEventImpl
import me.wietlol.wietbot.data.messages.models.models.MessageEditedEventImpl
import me.wietlol.wietbot.data.messages.models.models.MessageEvent
import me.wietlol.wietbot.data.messages.models.models.MessageEventList
import me.wietlol.wietbot.data.messages.models.models.MessageEventListImpl
import me.wietlol.wietbot.data.messages.models.models.MessagePostedEventImpl
import me.wietlol.wietbot.data.messages.models.models.MessageSourceImpl
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.MessageDeleted
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.MessageEdited
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.MessagePosted
import java.time.Instant
import kotlin.collections.HashMap

class MessageCache
{
	private val map: MutableMap<Int, MessageEventList> = HashMap()
	private val storageTime: Long = 600 // maybe move to configuration
	private val platform: String = "stack-overflow" // maybe move to configuration
	
	fun processMessage(message: MessagePosted): MessageEventList =
		messageListOf(message)
			.also { map[it.messageId] = it }
	
	fun processMessage(message: MessageEdited): MessageEventList?
	{
		val originalMessage: MessageEventList = map[message.messageId]
			?: return null // return if the message is not managed by the client
		
		return messageListOf(message, originalMessage)
			.also { map[it.messageId] = it }
	}
	
	fun processMessage(message: MessageDeleted): MessageEventList?
	{
		val originalMessage: MessageEventList = map[message.messageId]
			?: return null // return if the message is not managed by the client
		
		return messageListOf(message, originalMessage)
			.also { map[it.messageId] = it }
	}
	
	fun cleanup()
	{
		val currentTime = Instant.EPOCH.epochSecond
		val timeLimit = currentTime - storageTime
		
		map
			.entries
			.filter { it.value.events.first().timeStamp < timeLimit }
			.toList()
			.forEach { map.remove(it.key) }
	}
	
	private fun messageListOf(message: MessagePosted): MessageEventList =
		MessageEventListImpl(
			message.messageId,
			listOf(messageOf(message))
		)
	
	private fun messageOf(message: MessagePosted): MessageEvent =
		MessagePostedEventImpl(
			message.id,
			message.timeStamp,
			message.messageId,
			message.content,
			ChatUserImpl(
				message.userId,
				message.userName,
				platform
			),
			MessageSourceImpl(
				message.roomId,
				message.roomName
			)
		)
	
	private fun messageListOf(message: MessageEdited, originalMessage: MessageEventList): MessageEventList =
		MessageEventListImpl(
			message.messageId,
			originalMessage.events + messageOf(message)
		)
	
	private fun messageOf(message: MessageEdited): MessageEvent =
		MessageEditedEventImpl(
			message.id,
			message.timeStamp,
			message.messageId,
			message.content,
			ChatUserImpl(
				message.userId,
				message.userName,
				platform
			),
			MessageSourceImpl(
				message.roomId,
				message.roomName
			)
		)
	
	private fun messageListOf(message: MessageDeleted, originalMessage: MessageEventList): MessageEventList =
		MessageEventListImpl(
			message.messageId,
			originalMessage.events + messageOf(message)
		)
	
	private fun messageOf(message: MessageDeleted): ChatEvent =
		MessageDeletedEventImpl(
			message.id,
			message.timeStamp,
			message.messageId,
			ChatUserImpl(
				message.userId,
				message.userName,
				platform
			),
			MessageSourceImpl(
				message.roomId,
				message.roomName
			)
		)
}
