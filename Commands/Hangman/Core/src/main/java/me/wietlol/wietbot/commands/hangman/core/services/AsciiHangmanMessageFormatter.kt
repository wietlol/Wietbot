package me.wietlol.wietbot.commands.hangman.core.services

import me.wietlol.wietbot.commands.hangman.core.interfaces.HangmanMessageFormatter
import me.wietlol.wietbot.commands.hangman.core.models.HangmanState
import me.wietlol.wietbot.commands.hangman.core.models.HangmanStateChange
import me.wietlol.wietbot.commands.hangman.core.models.HangmanStateChange.Type.duplicateGuess
import me.wietlol.wietbot.commands.hangman.core.models.HangmanStateChange.Type.goodGuess
import me.wietlol.wietbot.commands.hangman.core.models.HangmanStateChange.Type.lose
import me.wietlol.wietbot.commands.hangman.core.models.HangmanStateChange.Type.win
import me.wietlol.wietbot.commands.hangman.core.models.HangmanStateChange.Type.wrongGuess

class AsciiHangmanMessageFormatter : HangmanMessageFormatter
{
	override fun formatMessage(stateChange: HangmanStateChange): String =
		when (stateChange.type)
		{
			lose -> createLoseMessage(stateChange)
			win -> createWinMessage(stateChange)
			goodGuess -> createGoodGuessMessage(stateChange)
			wrongGuess -> createWrongGuessMessage(stateChange)
			duplicateGuess -> createDuplicateGuessMessage(stateChange)
		}
	
	private fun createLoseMessage(stateChange: HangmanStateChange): String
	{
		val gallows = getGallowsAsciiArt(stateChange.newState)
		
		return "YOU LOSE\n$gallows\nthe word was ${stateChange.newState.word}"
	}
	
	private fun createWinMessage(stateChange: HangmanStateChange): String =
		"!!! A WINNER IS YOU !!!\nthe word was ${stateChange.newState.word}"
	
	private fun createGoodGuessMessage(stateChange: HangmanStateChange): String =
		getBoard(stateChange.newState)
	
	private fun createWrongGuessMessage(stateChange: HangmanStateChange): String
	{
		val gallows = getGallowsAsciiArt(stateChange.newState)
		val board = getBoard(stateChange.newState)
		
		return "$gallows\n$board"
	}
	
	private fun createDuplicateGuessMessage(stateChange: HangmanStateChange): String
	{
		return "this character has already been guessed\n" +
			"guessed characters: [${stateChange.newState.guessedCharacters.sorted().joinToString(" ")}]"
	}
	
	private fun getBoard(state: HangmanState): String =
		state.word.map { char ->
			when
			{
				char == ' '
					|| state.guessedCharacters.contains(char.toLowerCase()) -> char.toString()
				else -> "."
			}
		}.joinToString("")
	
	private fun getGallowsAsciiArt(state: HangmanState): String =
		gallowsAsciiArt[state.remainingLives]
	
	private val gallowsAsciiArt: List<String> =
		listOf("""  +---+
			%  |   |
			%  O   |
			% /|\  |
			% / \  |
			%      |
			%=========""",
			"""  +---+
			%  |   |
			%  O   |
			% /|\  |
			% /    |
			%      |
			%=========""",
			"""  +---+
			%  |   |
			%  O   |
			% /|\  |
			%      |
			%      |
			%=========""",
			"""  +---+
			%  |   |
			%  O   |
			% /|   |
			%      |
			%      |
			%=========""",
			"""  +---+
			%  |   |
			%  O   |
			%  |   |
			%      |
			%      |
			%=========""",
			"""  +---+
			%  |   |
			%  O   |
			%      |
			%      |
			%      |
			%=========""",
			"""  +---+
			%  |   |
			%      |
			%      |
			%      |
			%      |
			%=========""")
			.map { it.trimMargin("%") }
}
