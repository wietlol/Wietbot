// hash: #36b0683f
// data: serializationKey:972f98c6-48c7-4a87-bfdf-a0c3e22cdc01
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


interface SetUserRoleRequest : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("972f98c6-48c7-4a87-bfdf-a0c3e22cdc01")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val userId: Int
	
	val role: String
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is SetUserRoleRequest) return false
		
		if (userId != other.userId) return false
		if (role != other.role) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(userId)
			.with(role)
	
	override fun toJson(): String =
		"""{"userId":${userId.toJsonString()},"role":${role.toJsonString()}}"""
	
	fun duplicate(): SetUserRoleRequest
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
