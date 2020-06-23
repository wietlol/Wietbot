package me.wietlol.wietbot.commands.hangman.core.models

import me.wietlol.wietbot.commands.hangman.core.models.HangmanStateChange.Type.duplicateGuess
import me.wietlol.wietbot.commands.hangman.core.models.HangmanStateChange.Type.wrongGuess
import me.wietlol.wietbot.commands.hangman.core.models.HangmanStateChange.Type.goodGuess
import me.wietlol.wietbot.commands.hangman.core.models.HangmanStateChange.Type.lose
import me.wietlol.wietbot.commands.hangman.core.models.HangmanStateChange.Type.win

data class HangmanStateChange(
	val oldState: HangmanState,
	val newState: HangmanState
)
{
	val type: Type =
		when
		{
			hasDied() -> lose
			hasLostALife() -> wrongGuess
			hasGuessedAllCharacters() -> win
			hasEqualGuesses() -> duplicateGuess
			else -> goodGuess
		}
	
	private fun hasDied() =
		newState.remainingLives == 0
	
	private fun hasLostALife() =
		newState.remainingLives < oldState.remainingLives
	
	private fun hasGuessedAllCharacters() =
		newState.word.toLowerCase().all {
			it == ' ' || newState.guessedCharacters.contains(it)
		}
	
	private fun hasEqualGuesses() =
		oldState.guessedCharacters.size == newState.guessedCharacters.size
	
	fun hasEnded(): Boolean =
		listOf(lose, win).contains(type)
	
	enum class Type
	{
		lose,
		win,
		goodGuess,
		wrongGuess,
		duplicateGuess, ;
	}
}
