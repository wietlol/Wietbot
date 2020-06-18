package me.wietlol.wietbot.data.commands.core.interfaces

import me.wietlol.wietbot.data.commands.core.repository.models.Command

interface CommandRepository
{
	fun createCommand(keyword: String, listenerQueue: String): Command
	
	fun removeCommand(keyword: String)
	
	fun listCommands(): List<Command>
}
