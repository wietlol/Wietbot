// hash: #7064a85b
// data: serializationKey:88f199ce-4146-46d9-b072-cf6cf20e0d5e
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


interface ErrorResponse : BitSerializable, ClientCommandResponse, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("88f199ce-4146-46d9-b072-cf6cf20e0d5e")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val message: String
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is ErrorResponse) return false
		
		if (message != other.message) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(message)
	
	override fun toJson(): String =
		"""{"message":${message.toJsonString()}}"""
	
	override fun duplicate(): ErrorResponse
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
