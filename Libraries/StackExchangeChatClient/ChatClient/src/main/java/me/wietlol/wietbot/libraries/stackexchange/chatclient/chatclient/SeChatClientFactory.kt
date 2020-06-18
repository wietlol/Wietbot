package me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient

interface SeChatClientFactory
{
	fun create(credentials: SeCredentials): SeChatClient
}
