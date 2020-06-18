package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import java.util.*

class CreatePolicyRequestBuilder
{
	var name: String? = null
	
	fun build(): CreatePolicyRequest =
		CreatePolicyRequest.of(
			name!!
		)
}
