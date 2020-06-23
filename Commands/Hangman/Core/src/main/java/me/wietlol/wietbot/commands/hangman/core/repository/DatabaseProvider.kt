package me.wietlol.wietbot.commands.hangman.core.repository

import me.wietlol.wietbot.commands.hangman.core.repository.models.DbArchivedHangmanStates
import me.wietlol.wietbot.commands.hangman.core.repository.models.DbHangmanStates
import me.wietlol.wietbot.commands.hangman.core.repository.models.Words
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseProvider
{
	fun getDatabase(settings: DatabaseSettings) =
		Database.connect(settings.url, settings.driver, settings.user, settings.password)
			.also {
				transaction {
					SchemaUtils.create(DbHangmanStates, DbArchivedHangmanStates, Words)
				}
			}
}
