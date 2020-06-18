package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import java.util.*

class DetachRolePolicyRequestBuilder
{
	var role: String? = null
	var policy: String? = null
	
	fun build(): DetachRolePolicyRequest =
		DetachRolePolicyRequest.of(
			role!!,
			policy!!
		)
}
