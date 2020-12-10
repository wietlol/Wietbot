// hash: #fa777d24
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

import kotlin.DeprecationLevel.WARNING

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
	
	val localUserId: String
	
	val platform: Platform
	
	val role: String
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is SetUserRoleRequest) return false
		
		if (localUserId != other.localUserId) return false
		if (platform != other.platform) return false
		if (role != other.role) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(localUserId)
			.with(platform)
			.with(role)
	
	override fun toJson(): String =
		"""{"localUserId":${localUserId.toJsonString()},"platform":${platform.toJsonString()},"role":${role.toJsonString()}}"""
	
	fun duplicate(): SetUserRoleRequest
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	
	@Deprecated("backwards compatibility property", ReplaceWith("localUserId"), WARNING)
	val userId: String
		get() = localUserId
	
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
