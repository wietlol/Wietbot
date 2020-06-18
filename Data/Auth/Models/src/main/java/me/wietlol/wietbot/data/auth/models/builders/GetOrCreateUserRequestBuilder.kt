package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import java.util.*

class GetOrCreateUserRequestBuilder
{
	var user: User? = null
	
	fun build(): GetOrCreateUserRequest =
		GetOrCreateUserRequest.of(
			user!!
		)
}
