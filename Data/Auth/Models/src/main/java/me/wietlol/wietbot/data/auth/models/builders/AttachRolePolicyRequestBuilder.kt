package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import java.util.*

class AttachRolePolicyRequestBuilder
{
	var role: String? = null
	var policy: String? = null
	
	fun build(): AttachRolePolicyRequest =
		AttachRolePolicyRequest.of(
			role!!,
			policy!!
		)
}
