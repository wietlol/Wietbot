// hash: #9115f736
// data: serializationKey:8a9cefc6-ea29-4773-bbea-44c7e1d3e4b3
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


interface ListRolesResponse : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("8a9cefc6-ea29-4773-bbea-44c7e1d3e4b3")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val roles: List<Role>
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is ListRolesResponse) return false
		
		if (roles != other.roles) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(roles)
	
	override fun toJson(): String =
		"""{"roles":${roles.toJsonString()}}"""
	
	fun duplicate(): ListRolesResponse
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
