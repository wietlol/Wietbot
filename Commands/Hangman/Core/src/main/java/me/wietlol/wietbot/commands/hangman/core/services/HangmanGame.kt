package me.wietlol.wietbot.commands.hangman.core.services

import me.wietlol.wietbot.commands.hangman.core.interfaces.HangmanService
import me.wietlol.wietbot.commands.hangman.core.models.HangmanState
import me.wietlol.wietbot.commands.hangman.core.models.HangmanStateChange

class HangmanGame : HangmanService
{
	private val alphabet = 'a'..'z'
	
	override fun isGuessValid(character: Char): Boolean =
		alphabet.contains(character)
	
	override fun guessCharacter(state: HangmanState, character: Char): HangmanStateChange
	{
		val newState = when
		{
			state.guessedCharacters.contains(character) -> state
			state.word.toLowerCase().contains(character) -> HangmanState(
				state.word,
				state.guessedCharacters + character,
				state.remainingLives
			)
			else -> HangmanState(
				state.word,
				state.guessedCharacters + character,
				state.remainingLives - 1
			)
		}
		
		return HangmanStateChange(state, newState)
	}
}
