// hash: #976907e3
// data: serializationKey:54337246-e140-4583-a5f1-2c536cbcd0ba
// @formatter:off
package me.wietlol.wietbot.data.commands.models.models

import java.util.UUID
import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.utils.common.Jsonable
import me.wietlol.utils.common.emptyHashCode
import me.wietlol.utils.common.toJsonString
import me.wietlol.utils.common.with

// @formatter:on
// @tomplot:customCode:start:gAeCSq
// @tomplot:customCode:end
// @formatter:off


interface Message : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("54337246-e140-4583-a5f1-2c536cbcd0ba")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val id: Int
	
	val sender: ChatUser
	
	val fullText: String
	
	val source: MessageSource
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is Message) return false
		
		if (id != other.id) return false
		if (sender != other.sender) return false
		if (fullText != other.fullText) return false
		if (source != other.source) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(id)
			.with(sender)
			.with(fullText)
			.with(source)
	
	override fun toJson(): String =
		"""{"id":${id.toJsonString()},"sender":${sender.toJsonString()},"fullText":${fullText.toJsonString()},"source":${source.toJsonString()}}"""
	
	fun duplicate(): Message
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
