package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import java.util.*

class CreateRevokedAuthorityRequestBuilder
{
	var policy: String? = null
	var permission: String? = null
	var resource: String? = null
	
	fun build(): CreateRevokedAuthorityRequest =
		CreateRevokedAuthorityRequest.of(
			policy!!,
			permission!!,
			resource!!
		)
}
