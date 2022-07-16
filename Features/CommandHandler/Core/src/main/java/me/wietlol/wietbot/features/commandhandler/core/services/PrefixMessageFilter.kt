package me.wietlol.wietbot.features.commandhandler.core.services

import me.wietlol.wietbot.data.messages.models.models.MessageEvent
import me.wietlol.wietbot.data.messages.models.simpleText
import me.wietlol.wietbot.features.commandhandler.core.interfaces.MessageFilter

class PrefixMessageFilter(
	val prefix: String,
) : MessageFilter
{
	override fun apply(messageEvent: MessageEvent): Boolean =
		messageEvent.content.simpleText.startsWith(prefix)
}
