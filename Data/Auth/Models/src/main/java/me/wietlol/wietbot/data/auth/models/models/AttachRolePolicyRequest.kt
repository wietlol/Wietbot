package me.wietlol.wietbot.data.auth.models.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.common.emptyHashCode
import me.wietlol.common.Jsonable
import me.wietlol.common.toJson
import me.wietlol.common.with
import java.util.*
import me.wietlol.wietbot.data.auth.models.serializers.AttachRolePolicyRequestSerializer

interface AttachRolePolicyRequest : BitSerializable, Jsonable
{
	override val serializationKey: UUID
		get() = AttachRolePolicyRequestSerializer.modelId
	
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
		"""{"role":${role.toJson()},"policy":${policy.toJson()}}"""
	
	companion object
	{
		fun of(role: String, policy: String): AttachRolePolicyRequest =
			object : AttachRolePolicyRequest
			{
				override val role: String = role
				override val policy: String = policy
				
				override fun equals(other: Any?): Boolean =
					isEqualTo(other)
				
				override fun hashCode(): Int =
					computeHashCode()
				
				override fun toString(): String =
					toJson()
			}
	}
}
