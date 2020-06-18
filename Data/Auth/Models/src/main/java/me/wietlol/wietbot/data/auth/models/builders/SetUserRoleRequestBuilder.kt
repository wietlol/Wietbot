package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import java.util.*

class SetUserRoleRequestBuilder
{
	var userId: Int? = null
	var role: String? = null
	
	fun build(): SetUserRoleRequest =
		SetUserRoleRequest.of(
			userId!!,
			role!!
		)
}
