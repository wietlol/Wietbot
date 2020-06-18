package me.wietlol.wietbot.data.commands.models.builders

import me.wietlol.wietbot.data.commands.models.models.*
import java.util.*

class MessageBuilder
{
	var id: Int? = null
	var sender: User? = null
	var fullText: String? = null
	var source: Room? = null
	
	fun build(): Message =
		Message.of(
			id!!,
			sender!!,
			fullText!!,
			source!!
		)
}
