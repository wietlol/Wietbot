package me.wietlol.wietbot.data.commands.models.builders

import me.wietlol.wietbot.data.commands.models.models.*
import java.util.*

class CommandCallBuilder
{
	var commandKeyword: String? = null
	var argumentText: String? = null
	var message: Message? = null
	
	fun build(): CommandCall =
		CommandCall.of(
			commandKeyword!!,
			argumentText!!,
			message!!
		)
}
