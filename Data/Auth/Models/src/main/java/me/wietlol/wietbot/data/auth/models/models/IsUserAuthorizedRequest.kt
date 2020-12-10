// hash: #93954509
// data: serializationKey:9f4ae267-f5a4-416f-9033-7b4df27c4524
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


interface IsUserAuthorizedRequest : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("9f4ae267-f5a4-416f-9033-7b4df27c4524")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val userId: String
	
	val platform: Platform
	
	val permission: String
	
	val resource: String
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is IsUserAuthorizedRequest) return false
		
		if (userId != other.userId) return false
		if (platform != other.platform) return false
		if (permission != other.permission) return false
		if (resource != other.resource) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(userId)
			.with(platform)
			.with(permission)
			.with(resource)
	
	override fun toJson(): String =
		"""{"userId":${userId.toJsonString()},"platform":${platform.toJsonString()},"permission":${permission.toJsonString()},"resource":${resource.toJsonString()}}"""
	
	fun duplicate(): IsUserAuthorizedRequest
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
