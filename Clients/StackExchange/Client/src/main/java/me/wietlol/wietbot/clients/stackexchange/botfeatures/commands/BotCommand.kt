package me.wietlol.wietbot.clients.stackexchange.botfeatures.commands

import me.wietlol.wietbot.clients.stackexchange.botfeatures.CommandHandler
import me.wietlol.wietbot.data.commands.models.models.CommandCall

interface BotCommand
{
	val keyword: String
	
	fun execute(commandHandler: CommandHandler, commandCall: CommandCall)
}
