package me.wietlol.wietbot.commands.hangman.core.interfaces

import me.wietlol.wietbot.commands.hangman.core.models.HangmanState

interface HangmanStateFactory
{
	fun create(): HangmanState
}
