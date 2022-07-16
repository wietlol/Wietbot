package me.wietlol.wietbot.features.commandhandler.core.interfaces

import me.wietlol.wietbot.data.commands.models.models.CommandCall
import me.wietlol.wietbot.data.messages.models.models.MessageEventList

interface CommandCallParser
{
	fun parseCommand(event: MessageEventList): CommandCall?
}
