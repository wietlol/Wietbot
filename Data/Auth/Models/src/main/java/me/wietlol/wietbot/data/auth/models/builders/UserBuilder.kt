package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
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
