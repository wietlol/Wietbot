// hash: #852c8014
// data: serializationKey:06eab66c-8efd-4e20-b119-3e2f4964493c
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


interface User : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("06eab66c-8efd-4e20-b119-3e2f4964493c")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val id: Int
	
	val name: String
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is User) return false
		
		if (id != other.id) return false
		if (name != other.name) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(id)
			.with(name)
	
	override fun toJson(): String =
		"""{"id":${id.toJsonString()},"name":${name.toJsonString()}}"""
	
	fun duplicate(): User
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
