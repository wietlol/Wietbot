package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient

import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.SeChatClient
import java.io.Closeable

interface SeWebSocketClient : SeChatClient, Closeable
{
	fun reconnect(roomId: Int)
	
	fun joinRoom(roomId: Int): Room
	
	fun getRoom(roomId: Int): Room
	
	fun leaveRoom(roomId: Int)
	
	fun leaveAllRooms()
}
