package me.wietlol.wietbot.commands.hangman.core.interfaces

import me.wietlol.wietbot.commands.hangman.core.models.HangmanStateChange

interface HangmanMessageFormatter
{
	fun formatMessage(stateChange: HangmanStateChange): String
}
