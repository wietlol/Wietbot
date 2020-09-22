// hash: #e3733428
// data: serializationKey:4e83066f-87e4-442a-853c-dea9b60d7eb8
// @formatter:off
package me.wietlol.wietbot.data.auth.models.models

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
			get() = UUID.fromString("4e83066f-87e4-442a-853c-dea9b60d7eb8")
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
