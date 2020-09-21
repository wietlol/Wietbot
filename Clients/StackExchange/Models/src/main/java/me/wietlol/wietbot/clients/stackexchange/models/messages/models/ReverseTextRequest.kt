// hash: #bac53a5e
// data: serializationKey:2747f555-2b9e-43c4-9428-919e78f33952
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


interface ReverseTextRequest : BitSerializable, ClientCommandRequest, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("2747f555-2b9e-43c4-9428-919e78f33952")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val text: String
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is ReverseTextRequest) return false
		
		if (text != other.text) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(text)
	
	override fun toJson(): String =
		"""{"text":${text.toJsonString()}}"""
	
	override fun duplicate(): ReverseTextRequest
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
