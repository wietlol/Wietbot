package me.wietlol.wietbot.clients.stackexchange.botfeatures.commands

import me.wietlol.wietbot.clients.stackexchange.botfeatures.CommandHandler
import me.wietlol.wietbot.data.commands.models.models.CommandCall

data class InternalCommand(
	override val keyword: String,
	val action: CommandHandler.(CommandCall) -> Unit
) : BotCommand
{
	override fun execute(commandHandler: CommandHandler, commandCall: CommandCall): Unit = commandHandler.action(commandCall)
}
