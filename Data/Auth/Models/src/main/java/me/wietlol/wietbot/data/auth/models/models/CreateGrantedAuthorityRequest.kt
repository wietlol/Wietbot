// hash: #d4b6515d
// data: serializationKey:12fa8e29-7aa1-4616-a962-e401e3337d43
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


interface CreateGrantedAuthorityRequest : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("12fa8e29-7aa1-4616-a962-e401e3337d43")
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
		if (other !is CreateGrantedAuthorityRequest) return false
		
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
	
	fun duplicate(): CreateGrantedAuthorityRequest
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
