package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import java.util.*

class IsUserAuthorizedResponseBuilder
{
	var isAuthorized: Boolean? = null
	
	fun build(): IsUserAuthorizedResponse =
		IsUserAuthorizedResponse.of(
			isAuthorized!!
		)
}
