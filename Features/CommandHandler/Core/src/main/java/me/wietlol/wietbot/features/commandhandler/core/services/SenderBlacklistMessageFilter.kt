package me.wietlol.wietbot.features.commandhandler.core.services

import me.wietlol.wietbot.data.messages.models.models.MessageEvent
import me.wietlol.wietbot.features.commandhandler.core.interfaces.MessageFilter

class SenderBlacklistMessageFilter(
	val blacklist: List<String>,
) : MessageFilter
{
	override fun apply(messageEvent: MessageEvent): Boolean =
		blacklist.contains(messageEvent.user.id).not()
}
