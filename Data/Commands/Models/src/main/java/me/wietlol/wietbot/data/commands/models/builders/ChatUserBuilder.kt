// hash: #52f57bbc
// @formatter:off
package me.wietlol.wietbot.data.commands.models.builders

import me.wietlol.wietbot.data.commands.models.models.*
import me.wietlol.wietbot.data.commands.models.models.ChatUserImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class ChatUserBuilder
{
	var id: Int?
		= null
	
	var name: String?
		= null
	
	var platform: String?
		= null
	
	fun build(): ChatUser =
		ChatUserImpl(
			id!!,
			name!!,
			platform!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
