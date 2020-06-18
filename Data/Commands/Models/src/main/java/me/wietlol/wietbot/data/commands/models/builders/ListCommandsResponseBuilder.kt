package me.wietlol.wietbot.data.commands.models.builders

import me.wietlol.wietbot.data.commands.models.models.*
import java.util.*

class ListCommandsResponseBuilder
{
	var commands: MutableList<Command> = mutableListOf()
	
	fun build(): ListCommandsResponse =
		ListCommandsResponse.of(
			commands.toList()
		)
}
