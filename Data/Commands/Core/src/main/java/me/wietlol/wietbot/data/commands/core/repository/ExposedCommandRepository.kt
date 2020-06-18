package me.wietlol.wietbot.data.commands.core.repository

import me.wietlol.wietbot.data.commands.core.interfaces.CommandRepository
import me.wietlol.wietbot.data.commands.core.repository.models.Command
import me.wietlol.wietbot.data.commands.core.repository.models.Commands
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

class ExposedCommandRepository(
	@Suppress("unused") val database: Database // make sure a database connection is set up
) : CommandRepository
{
	override fun createCommand(keyword: String, listenerQueue: String): Command =
		transaction {
			Command.new {
				this.keyword = keyword
				this.listenerQueue = listenerQueue
			}
		}
	
	override fun removeCommand(keyword: String) =
		transaction {
			Command.find { Commands.keyword eq keyword }
				.single()
				.delete()
		}
	
	override fun listCommands(): List<Command> =
		transaction {
			Command.all().toList()
		}
}
