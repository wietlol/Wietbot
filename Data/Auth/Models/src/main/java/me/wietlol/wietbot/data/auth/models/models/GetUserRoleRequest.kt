// hash: #e699eb0d
// data: serializationKey:55e26066-90cd-430c-bec5-740f22858838
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


interface GetUserRoleRequest : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("55e26066-90cd-430c-bec5-740f22858838")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val localUserId: String
	
	val platform: Platform
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is GetUserRoleRequest) return false
		
		if (localUserId != other.localUserId) return false
		if (platform != other.platform) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(localUserId)
			.with(platform)
	
	override fun toJson(): String =
		"""{"localUserId":${localUserId.toJsonString()},"platform":${platform.toJsonString()}}"""
	
	fun duplicate(): GetUserRoleRequest
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	
	@Deprecated("backwards compatibility property", ReplaceWith("localUserId"), WARNING)
	val userId: String
		get() = localUserId
	
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
