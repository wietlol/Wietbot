package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models

interface SeChatEvent
{
	val id: Int
	val eventType: EventType
	val roomId: Int
	val roomName: String
}
