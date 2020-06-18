package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient

import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.SeCredentials

interface SeWebSocketClientFactory
{
	fun create(credentials: SeCredentials): SeWebSocketClient
}
