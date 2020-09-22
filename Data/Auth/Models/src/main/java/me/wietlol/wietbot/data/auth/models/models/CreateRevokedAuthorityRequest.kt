// hash: #9c2b16cd
// data: serializationKey:73907926-4668-4397-97b3-631c993d9a61
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


interface CreateRevokedAuthorityRequest : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("73907926-4668-4397-97b3-631c993d9a61")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val policy: String
	
	val permission: String
	
	val resource: String
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is CreateRevokedAuthorityRequest) return false
		
		if (policy != other.policy) return false
		if (permission != other.permission) return false
		if (resource != other.resource) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(policy)
			.with(permission)
			.with(resource)
	
	override fun toJson(): String =
		"""{"policy":${policy.toJsonString()},"permission":${permission.toJsonString()},"resource":${resource.toJsonString()}}"""
	
	fun duplicate(): CreateRevokedAuthorityRequest
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
