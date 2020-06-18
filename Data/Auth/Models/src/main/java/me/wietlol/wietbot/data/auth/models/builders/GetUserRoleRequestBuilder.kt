package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import java.util.*

class GetUserRoleRequestBuilder
{
	var userId: Int? = null
	
	fun build(): GetUserRoleRequest =
		GetUserRoleRequest.of(
			userId!!
		)
}
