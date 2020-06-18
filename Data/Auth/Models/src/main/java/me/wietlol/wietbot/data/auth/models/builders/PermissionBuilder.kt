package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import java.util.*

class PermissionBuilder
{
	var id: Int? = null
	var name: String? = null
	
	fun build(): Permission =
		Permission.of(
			id!!,
			name!!
		)
}
