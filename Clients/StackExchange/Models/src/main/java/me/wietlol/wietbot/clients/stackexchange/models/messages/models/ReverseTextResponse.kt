// hash: #eedc00c4
// data: serializationKey:ca6d9fed-ea60-4aba-81c9-f15dbe6bfd58
// @formatter:off
package me.wietlol.wietbot.clients.stackexchange.models.messages.models

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


interface ReverseTextResponse : BitSerializable, ClientCommandResponse, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("ca6d9fed-ea60-4aba-81c9-f15dbe6bfd58")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val text: String
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is ReverseTextResponse) return false
		
		if (text != other.text) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(text)
	
	override fun toJson(): String =
		"""{"text":${text.toJsonString()}}"""
	
	override fun duplicate(): ReverseTextResponse
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
