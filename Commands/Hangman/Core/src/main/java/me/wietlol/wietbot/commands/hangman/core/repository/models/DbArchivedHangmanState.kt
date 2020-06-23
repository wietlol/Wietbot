package me.wietlol.wietbot.commands.hangman.core.repository.models

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import org.joda.time.DateTime

object DbArchivedHangmanStates : IntIdTable("hangman_archivedState")
{
	val archivedGameId = uuid("archivedGameId")
	val gameId = varchar("gameId", 32)
	val moment = datetime("moment")
	val word = varchar("word", 32)
	val guessedCharacters = varchar("guessedCharacters", 26)
	val remainingLives = integer("remainingLives")
}

class DbArchivedHangmanState(id: EntityID<Int>) : IntEntity(id)
{
	companion object : IntEntityClass<DbArchivedHangmanState>(DbArchivedHangmanStates)
	
	var archivedGameId by DbArchivedHangmanStates.archivedGameId
	var gameId by DbArchivedHangmanStates.gameId
	var moment: DateTime by DbArchivedHangmanStates.moment
	var word by DbArchivedHangmanStates.word
	var guessedCharacters by DbArchivedHangmanStates.guessedCharacters
	var remainingLives by DbArchivedHangmanStates.remainingLives
}
