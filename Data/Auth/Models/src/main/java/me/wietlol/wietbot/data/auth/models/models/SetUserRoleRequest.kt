package me.wietlol.wietbot.data.auth.models.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.common.emptyHashCode
import me.wietlol.common.Jsonable
import me.wietlol.common.toJson
import me.wietlol.common.with
import java.util.*
import me.wietlol.wietbot.data.auth.models.serializers.SetUserRoleRequestSerializer

interface SetUserRoleRequest : BitSerializable, Jsonable
{
	override val serializationKey: UUID
		get() = SetUserRoleRequestSerializer.modelId
	
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
		"""{"userId":${userId.toJson()},"role":${role.toJson()}}"""
	
	companion object
	{
		fun of(userId: Int, role: String): SetUserRoleRequest =
			object : SetUserRoleRequest
			{
				override val userId: Int = userId
				override val role: String = role
				
				override fun equals(other: Any?): Boolean =
					isEqualTo(other)
				
				override fun hashCode(): Int =
					computeHashCode()
				
				override fun toString(): String =
					toJson()
			}
	}
}
