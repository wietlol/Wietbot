package me.wietlol.wietbot.commands.hangman.core.services

import me.wietlol.wietbot.commands.hangman.core.interfaces.HangmanRepository
import me.wietlol.wietbot.commands.hangman.core.interfaces.HangmanStateFactory
import me.wietlol.wietbot.commands.hangman.core.models.HangmanState

class DbBackedHangmanStateFactory(
	val repository: HangmanRepository
) : HangmanStateFactory
{
	private val initialLives = 7
	
	override fun create(): HangmanState =
		HangmanState(
			getWord(),
			emptySet(),
			initialLives
		)
	
	private fun getWord(): String =
		repository
			.getWords()
			.random()
}
