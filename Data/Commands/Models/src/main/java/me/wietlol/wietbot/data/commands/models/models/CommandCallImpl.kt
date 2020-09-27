// hash: #9aeae69a
// @formatter:off
package me.wietlol.wietbot.data.commands.models.models

import me.wietlol.wietbot.data.commands.models.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off


data class CommandCallImpl(
	override val commandKeyword: String,
	override val argumentText: String,
	override val message: Message,
	override val options: Map<String, String>,
) : CommandCall
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): CommandCallImpl =
		copy(
			commandKeyword = commandKeyword,
			argumentText = argumentText,
			message = message.duplicate(),
			options = options.map { it.key to it.value }.toMap(HashMap()),
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
