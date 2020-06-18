package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models

interface MessageEvent : SeChatEvent
{
	val content: String
	val userId: Int
	val userName: String
}
