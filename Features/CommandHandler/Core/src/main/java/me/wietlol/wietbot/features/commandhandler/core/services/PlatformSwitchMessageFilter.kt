package me.wietlol.wietbot.features.commandhandler.core.services

import me.wietlol.wietbot.data.messages.models.models.MessageEvent
import me.wietlol.wietbot.features.commandhandler.core.interfaces.MessageFilter

class PlatformSwitchMessageFilter(
	val filterMap: Map<String, MessageFilter>,
) : MessageFilter
{
	override fun apply(messageEvent: MessageEvent): Boolean =
		filterMap[messageEvent.platform]
			?.apply(messageEvent)
			?: false
}
