package me.wietlol.wietbot.commands.hangman.core.models

data class HangmanState(
	val word: String,
	val guessedCharacters: Set<Char>,
	val remainingLives: Int
)
