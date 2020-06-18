package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import java.util.*

class GetUserRoleResponseBuilder
{
	var role: Role? = null
	
	fun build(): GetUserRoleResponse =
		GetUserRoleResponse.of(
			role!!
		)
}
