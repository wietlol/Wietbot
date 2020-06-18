package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models

data class BulkChatEvent(
	val events: List<SeChatEvent>
)
