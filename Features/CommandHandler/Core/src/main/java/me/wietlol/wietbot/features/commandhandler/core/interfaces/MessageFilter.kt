package me.wietlol.wietbot.features.commandhandler.core.interfaces

import me.wietlol.wietbot.data.messages.models.models.MessageEvent

interface MessageFilter
{
	fun apply(messageEvent: MessageEvent): Boolean
}
