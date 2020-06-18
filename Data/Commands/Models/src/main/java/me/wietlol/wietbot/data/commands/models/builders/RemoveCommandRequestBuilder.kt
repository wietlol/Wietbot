package me.wietlol.wietbot.data.commands.models.builders

import me.wietlol.wietbot.data.commands.models.models.*
import java.util.*

class RemoveCommandRequestBuilder
{
	var keyword: String? = null
	
	fun build(): RemoveCommandRequest =
		RemoveCommandRequest.of(
			keyword!!
		)
}
