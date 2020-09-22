// hash: #5f454301
// data: serializationKey:7bf99c84-fdf0-422b-907a-50e542c86f37
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


interface AttachRolePolicyRequest : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("7bf99c84-fdf0-422b-907a-50e542c86f37")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val role: String
	
	val policy: String
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is AttachRolePolicyRequest) return false
		
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
	
	fun duplicate(): AttachRolePolicyRequest
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
