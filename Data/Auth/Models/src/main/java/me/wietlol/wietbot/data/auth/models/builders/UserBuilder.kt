// hash: #b8ed69ad
// @formatter:off
package me.wietlol.wietbot.data.auth.models.builders

import me.wietlol.wietbot.data.auth.models.models.*
import me.wietlol.wietbot.data.auth.models.models.UserImpl

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
	
	var stackExchangeId: String?
		= null
	
	var stackExchangeName: String?
		= null
	
	var discordId: String?
		= null
	
	var discordName: String?
		= null
	
	var wietbotWebsiteId: String?
		= null
	
	var wietbotWebsiteName: String?
		= null
	
	var role: Int?
		= null
	
	fun build(): User =
		UserImpl(
			localId!!,
			localName!!,
			platform!!,
			internalId!!,
			stackExchangeId!!,
			stackExchangeName!!,
			discordId!!,
			discordName!!,
			wietbotWebsiteId!!,
			wietbotWebsiteName!!,
			role!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
