package me.wietlol.wietbot.commands.hangman.core.repository.models

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import org.joda.time.DateTime

object DbHangmanStates : IntIdTable("hangman_state")
{
	val gameId = varchar("gameId", 32)
	val moment = datetime("moment")
	val word = varchar("word", 32)
	val guessedCharacters = varchar("guessedCharacters", 26)
	val remainingLives = integer("remainingLives")
}

class DbHangmanState(id: EntityID<Int>) : IntEntity(id)
{
	companion object : IntEntityClass<DbHangmanState>(DbHangmanStates)
	
	var gameId by DbHangmanStates.gameId
	var moment: DateTime by DbHangmanStates.moment
	var word by DbHangmanStates.word
	var guessedCharacters by DbHangmanStates.guessedCharacters
	var remainingLives by DbHangmanStates.remainingLives
}
