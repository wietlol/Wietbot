// hash: #329c8e3e
// @formatter:off
package me.wietlol.wietbot.data.commands.models.models

import me.wietlol.wietbot.data.commands.models.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off


data class CommandImpl(
	override val keyword: String,
	override val listenerQueue: String,
) : Command
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): CommandImpl =
		copy(
			keyword = keyword,
			listenerQueue = listenerQueue,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
