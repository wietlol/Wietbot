package me.wietlol.wietbot.data.auth.models.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.common.emptyHashCode
import me.wietlol.common.Jsonable
import me.wietlol.common.toJson
import me.wietlol.common.with
import java.util.*
import me.wietlol.wietbot.data.auth.models.serializers.CreateRevokedAuthorityRequestSerializer

interface CreateRevokedAuthorityRequest : BitSerializable, Jsonable
{
	override val serializationKey: UUID
		get() = CreateRevokedAuthorityRequestSerializer.modelId
	
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
		"""{"policy":${policy.toJson()},"permission":${permission.toJson()},"resource":${resource.toJson()}}"""
	
	companion object
	{
		fun of(policy: String, permission: String, resource: String): CreateRevokedAuthorityRequest =
			object : CreateRevokedAuthorityRequest
			{
				override val policy: String = policy
				override val permission: String = permission
				override val resource: String = resource
				
				override fun equals(other: Any?): Boolean =
					isEqualTo(other)
				
				override fun hashCode(): Int =
					computeHashCode()
				
				override fun toString(): String =
					toJson()
			}
	}
}
