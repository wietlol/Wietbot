package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import java.util.*

class CreateRoleRequestBuilder
{
	var name: String? = null
	
	fun build(): CreateRoleRequest =
		CreateRoleRequest.of(
			name!!
		)
}
