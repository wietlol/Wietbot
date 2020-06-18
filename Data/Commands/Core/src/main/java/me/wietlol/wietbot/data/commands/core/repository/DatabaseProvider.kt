package me.wietlol.wietbot.data.commands.core.repository

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SchemaUtils
import me.wietlol.wietbot.data.commands.core.repository.models.Commands

object DatabaseProvider
{
	fun getDatabase(settings: DatabaseSettings) =
		Database.connect(settings.url, settings.driver, settings.user, settings.password)
			.also {
				transaction {
					SchemaUtils.create(Commands)
				}
			}
}
