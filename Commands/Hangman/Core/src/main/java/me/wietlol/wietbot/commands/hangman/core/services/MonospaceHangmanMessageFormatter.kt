package me.wietlol.wietbot.commands.hangman.core.services

import me.wietlol.wietbot.commands.hangman.core.interfaces.HangmanMessageFormatter
import me.wietlol.wietbot.commands.hangman.core.models.HangmanStateChange

class MonospaceHangmanMessageFormatter(
	val messageFormatter: HangmanMessageFormatter
) : HangmanMessageFormatter
{
	override fun formatMessage(stateChange: HangmanStateChange): String =
		messageFormatter.formatMessage(stateChange)
			.lines()
			.joinToString("\n") { "    $it" }
}
