// hash: #66355702
// data: serializationKey:ac73a56a-7485-45da-bb90-2b162612758d
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


interface DetachRolePolicyRequest : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("ac73a56a-7485-45da-bb90-2b162612758d")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val role: String
	
	val policy: String
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is DetachRolePolicyRequest) return false
		
		if (role != other.role) return false
		if (policy != other.policy) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(role)
			.with(policy)
	
	override fun toJson(): String =
		"""{"role":${role.toJsonString()},"policy":${policy.toJsonString()}}"""
	
	fun duplicate(): DetachRolePolicyRequest
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
