package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import java.util.*

class ListPermissionsResponseBuilder
{
	var isAuthorized: MutableList<Permission> = mutableListOf()
	
	fun build(): ListPermissionsResponse =
		ListPermissionsResponse.of(
			isAuthorized.toList()
		)
}
