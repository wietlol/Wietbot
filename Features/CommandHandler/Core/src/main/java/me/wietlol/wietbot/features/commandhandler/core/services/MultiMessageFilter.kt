package me.wietlol.wietbot.features.commandhandler.core.services

import me.wietlol.wietbot.data.messages.models.models.MessageEvent
import me.wietlol.wietbot.features.commandhandler.core.interfaces.MessageFilter

class MultiMessageFilter(
	val filters: List<MessageFilter>,
) : MessageFilter
{
	override fun apply(messageEvent: MessageEvent): Boolean =
		filters.all { it.apply(messageEvent) }
}
