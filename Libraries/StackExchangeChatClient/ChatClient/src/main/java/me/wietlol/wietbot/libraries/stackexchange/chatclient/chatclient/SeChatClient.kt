package me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient

interface SeChatClient
{
	fun sendMessage(roomId: Int, text: String): Int
	
	fun editMessage(messageId: Int, text: String)
}
