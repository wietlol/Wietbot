package me.wietlol.wietbot.commands.hangman.core.repository

import me.wietlol.wietbot.commands.hangman.core.interfaces.HangmanRepository
import me.wietlol.wietbot.commands.hangman.core.models.HangmanState
import me.wietlol.wietbot.commands.hangman.core.repository.models.DbArchivedHangmanState
import me.wietlol.wietbot.commands.hangman.core.repository.models.DbArchivedHangmanStates
import me.wietlol.wietbot.commands.hangman.core.repository.models.DbHangmanState
import me.wietlol.wietbot.commands.hangman.core.repository.models.DbHangmanStates
import me.wietlol.wietbot.commands.hangman.core.repository.models.Word
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SortOrder.DESC
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.util.*

class ExposedHangmanRepository(
	@Suppress("unused") val database: Database // make sure a database connection is set up
) : HangmanRepository
{
	override fun insertState(gameId: String, state: HangmanState) =
		transaction {
			DbHangmanState.new {
				this.gameId = gameId
				moment = DateTime.now(DateTimeZone.UTC)
				word = state.word
				guessedCharacters = state.guessedCharacters.joinToString("")
				remainingLives = state.remainingLives
			}
		}
	
	override fun getLatestState(gameId: String): HangmanState? =
		transaction {
			DbHangmanState
				.find { DbHangmanStates.gameId eq gameId }
				.limit(1)
				.orderBy(DbHangmanStates.moment to DESC)
				.firstOrNull()
		}?.run {
			HangmanState(
				word,
				guessedCharacters.toCharArray().toSet(),
				remainingLives
			)
		}
	
	override fun archive(gameId: String) =
		transaction {
			val states = DbHangmanState.find { DbHangmanStates.gameId eq gameId }
			
			val archiveId = UUID.randomUUID()
			
			states.forEach { state ->
				DbArchivedHangmanState.new {
					archivedGameId = archiveId
					this.gameId = state.gameId
					moment = state.moment
					word = state.word
					guessedCharacters = state.guessedCharacters
					remainingLives = state.remainingLives
				}
				
				state.delete()
			}
		}
	
	override fun getWords(): List<String> =
		transaction {
			Word.all()
				.map { it.text }
		}
}
