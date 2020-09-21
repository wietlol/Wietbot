// hash: #8dc47e1b
// @formatter:off
package me.wietlol.wietbot.clients.stackexchange.models.messages.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.utils.common.Jsonable

// @formatter:on
// @tomplot:customCode:start:WTcTph
// @tomplot:customCode:end
// @formatter:off


interface MessageEvent : BitSerializable, ChatEvent, Jsonable
{
	val messageId: Int
	
	override fun duplicate(): MessageEvent
	
	// @formatter:on
	// @tomplot:customCode:start:0MOZ71
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
