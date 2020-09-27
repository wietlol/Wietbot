// hash: #4a21f83f
// @formatter:off
package me.wietlol.wietbot.data.commands.models.models

import me.wietlol.wietbot.data.commands.models.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off


class ListCommandsRequestImpl : ListCommandsRequest
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): ListCommandsRequestImpl =
		this
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
