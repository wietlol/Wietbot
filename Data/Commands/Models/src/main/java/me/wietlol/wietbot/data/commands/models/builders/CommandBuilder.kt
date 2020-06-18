package me.wietlol.wietbot.data.commands.models.builders

import me.wietlol.wietbot.data.commands.models.models.*
import java.util.*

class CommandBuilder
{
	var keyword: String? = null
	var listenerQueue: String? = null
	
	fun build(): Command =
		Command.of(
			keyword!!,
			listenerQueue!!
		)
}
