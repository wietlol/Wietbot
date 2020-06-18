package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import java.util.*

class RoleBuilder
{
	var id: Int? = null
	var name: String? = null
	
	fun build(): Role =
		Role.of(
			id!!,
			name!!
		)
}
