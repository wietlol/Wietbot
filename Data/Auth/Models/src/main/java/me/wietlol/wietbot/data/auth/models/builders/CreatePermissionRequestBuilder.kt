// hash: #e10e032a
// @formatter:off
package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.DefaultCreatePermissionRequest

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class CreatePermissionRequestBuilder
{
	var name: String?
		= null
	
	fun build(): CreatePermissionRequest =
		DefaultCreatePermissionRequest(
			name!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
