package me.wietlol.wietbot.commands.hangman.core.interfaces

import me.wietlol.wietbot.commands.hangman.core.models.HangmanState
import me.wietlol.wietbot.commands.hangman.core.models.HangmanStateChange

interface HangmanService
{
	fun isGuessValid(character: Char): Boolean
	
	fun guessCharacter(state: HangmanState, character: Char): HangmanStateChange
}
