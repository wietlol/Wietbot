package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import java.util.*

class GetOrCreateUserResponseBuilder
{
	var user: User? = null
	
	fun build(): GetOrCreateUserResponse =
		GetOrCreateUserResponse.of(
			user!!
		)
}
