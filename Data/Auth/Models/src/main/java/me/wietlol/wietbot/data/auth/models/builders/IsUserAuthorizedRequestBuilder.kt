package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import java.util.*

class IsUserAuthorizedRequestBuilder
{
	var userId: Int? = null
	var permission: String? = null
	var resource: String? = "*"
	
	fun build(): IsUserAuthorizedRequest =
		IsUserAuthorizedRequest.of(
			userId!!,
			permission!!,
			resource!!
		)
}
