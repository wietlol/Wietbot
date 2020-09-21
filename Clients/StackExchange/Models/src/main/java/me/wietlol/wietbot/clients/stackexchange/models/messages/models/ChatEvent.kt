// hash: #06643bbd
// @formatter:off
package me.wietlol.wietbot.clients.stackexchange.models.messages.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.utils.common.Jsonable

// @formatter:on
// @tomplot:customCode:start:WTcTph
// @tomplot:customCode:end
// @formatter:off


interface ChatEvent : BitSerializable, Jsonable
{
	val id: Int
	
	val timeStamp: Long
	
	val eventType: ChatEventType
	
	val room: Room
	
	fun duplicate(): ChatEvent
	
	// @formatter:on
	// @tomplot:customCode:start:0MOZ71
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
