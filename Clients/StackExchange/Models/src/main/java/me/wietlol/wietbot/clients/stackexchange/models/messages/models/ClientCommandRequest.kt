// hash: #3097fee3
// @formatter:off
package me.wietlol.wietbot.clients.stackexchange.models.messages.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.utils.common.Jsonable

// @formatter:on
// @tomplot:customCode:start:WTcTph
// @tomplot:customCode:end
// @formatter:off


interface ClientCommandRequest : BitSerializable, Jsonable
{
	fun duplicate(): ClientCommandRequest
	
	// @formatter:on
	// @tomplot:customCode:start:0MOZ71
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
