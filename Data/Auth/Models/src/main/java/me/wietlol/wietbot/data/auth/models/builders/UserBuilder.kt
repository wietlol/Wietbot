// hash: #0d43a210
// @formatter:off
package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.DefaultUser

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class UserBuilder
{
	var localId: String?
		= null
	
	var localName: String?
		= null
	
	var platform: Platform?
		= null
	
	var internalId: Long?
		= null
	
	var stackOverflowId: String?
		= null
	
	var stackOverflowName: String?
		= null
	
	var discordId: String?
		= null
	
	var discordName: String?
		= null
	
	var wietbotWebsiteId: String?
		= null
	
	var wietbotWebsiteName: String?
		= null
	
	fun build(): User =
		DefaultUser(
			localId!!,
			localName!!,
			platform!!,
			internalId!!,
			stackOverflowId,
			stackOverflowName,
			discordId,
			discordName,
			wietbotWebsiteId,
			wietbotWebsiteName,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
