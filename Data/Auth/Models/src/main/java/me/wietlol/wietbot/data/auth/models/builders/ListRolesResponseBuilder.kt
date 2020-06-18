package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import java.util.*

class ListRolesResponseBuilder
{
	var roles: MutableList<Role> = mutableListOf()
	
	fun build(): ListRolesResponse =
		ListRolesResponse.of(
			roles.toList()
		)
}
