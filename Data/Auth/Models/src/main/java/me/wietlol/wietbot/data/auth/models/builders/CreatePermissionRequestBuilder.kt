package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import java.util.*

class CreatePermissionRequestBuilder
{
	var name: String? = null
	
	fun build(): CreatePermissionRequest =
		CreatePermissionRequest.of(
			name!!
		)
}
