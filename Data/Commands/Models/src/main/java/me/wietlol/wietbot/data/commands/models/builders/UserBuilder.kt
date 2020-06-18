package me.wietlol.wietbot.data.commands.models.builders

import me.wietlol.wietbot.data.commands.models.models.*
import java.util.*

class UserBuilder
{
	var id: Int? = null
	var name: String? = null
	
	fun build(): User =
		User.of(
			id!!,
			name!!
		)
}
