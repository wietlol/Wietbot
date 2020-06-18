package me.wietlol.wietbot.data.commands.models.builders

import me.wietlol.wietbot.data.commands.models.models.*
import java.util.*

class CreateCommandRequestBuilder
{
	var keyword: String? = null
	var listenerQueue: String? = null
	
	fun build(): CreateCommandRequest =
		CreateCommandRequest.of(
			keyword!!,
			listenerQueue!!
		)
}
