// hash: #714b14ec
// @formatter:off
package me.wietlol.wietbot.data.commands.models.models

import me.wietlol.wietbot.data.commands.models.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off


data class ListCommandsResponseImpl(
	override val commands: List<Command>,
) : ListCommandsResponse
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): ListCommandsResponseImpl =
		copy(
			commands = commands.map { it.duplicate() }.toMutableList(),
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
