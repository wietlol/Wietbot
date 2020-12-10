// hash: #3d598b76
// @formatter:off
package me.wietlol.wietbot.data.auth.models.models

import me.wietlol.wietbot.data.auth.models.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off


data class DefaultPlatform(
	override val name: String,
) : Platform
{
	companion object
	{
		val stackOverflow: DefaultPlatform
			= DefaultPlatform("stack-overflow")
		
		val discord: DefaultPlatform
			= DefaultPlatform("discord")
		
		val wietbotWebsite: DefaultPlatform
			= DefaultPlatform("wietbot-website")
	}
	
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): DefaultPlatform =
		copy(
			name = name,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
