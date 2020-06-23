package me.wietlol.wietbot.commands.hangman.core.interfaces

import me.wietlol.wietbot.commands.hangman.core.models.HangmanState
import me.wietlol.wietbot.commands.hangman.core.repository.models.DbHangmanState

interface HangmanRepository
{
	fun insertState(gameId: String, state: HangmanState): DbHangmanState
	
	fun getLatestState(gameId: String): HangmanState?
	
	fun archive(gameId: String)
	
	fun getWords(): List<String>
}
