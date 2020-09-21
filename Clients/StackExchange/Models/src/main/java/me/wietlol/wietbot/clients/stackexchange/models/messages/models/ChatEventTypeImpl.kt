// hash: #0d0bd790
// @formatter:off
package me.wietlol.wietbot.clients.stackexchange.models.messages.models

import me.wietlol.wietbot.clients.stackexchange.models.messages.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off


data class ChatEventTypeImpl(
	override val id: Int,
) : ChatEventType
{
	companion object
	{
		val messagePosted: ChatEventTypeImpl
			= ChatEventTypeImpl(1)
		
		val messageEdited: ChatEventTypeImpl
			= ChatEventTypeImpl(2)
		
		val userJoined: ChatEventTypeImpl
			= ChatEventTypeImpl(3)
		
		val userLeft: ChatEventTypeImpl
			= ChatEventTypeImpl(4)
		
		val messageStarred: ChatEventTypeImpl
			= ChatEventTypeImpl(6)
		
		val userMentioned: ChatEventTypeImpl
			= ChatEventTypeImpl(8)
		
		val messageDeleted: ChatEventTypeImpl
			= ChatEventTypeImpl(10)
		
		val replyPosted: ChatEventTypeImpl
			= ChatEventTypeImpl(18)
		
		val messageMovedOut: ChatEventTypeImpl
			= ChatEventTypeImpl(19)
		
		val messageMovedIn: ChatEventTypeImpl
			= ChatEventTypeImpl(20)
	}
	
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): ChatEventTypeImpl =
		copy(
			id = id,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
