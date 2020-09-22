// hash: #5e9ecf87
// data: serializationKey:4e45c23c-4239-4794-9f75-3bfd1832e8be
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


interface GetUserRoleResponse : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("4e45c23c-4239-4794-9f75-3bfd1832e8be")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val role: Role
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is GetUserRoleResponse) return false
		
		if (role != other.role) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(role)
	
	override fun toJson(): String =
		"""{"role":${role.toJsonString()}}"""
	
	fun duplicate(): GetUserRoleResponse
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
