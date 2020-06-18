package me.wietlol.wietbot.data.auth.models.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.common.emptyHashCode
import me.wietlol.common.Jsonable
import me.wietlol.common.toJson
import me.wietlol.common.with
import java.util.*
import me.wietlol.wietbot.data.auth.models.serializers.GetUserRoleRequestSerializer

interface GetUserRoleRequest : BitSerializable, Jsonable
{
	override val serializationKey: UUID
		get() = GetUserRoleRequestSerializer.modelId
	
	val userId: Int
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is GetUserRoleRequest) return false
		
		if (userId != other.userId) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(userId)
	
	override fun toJson(): String =
		"""{"userId":${userId.toJson()}}"""
	
	companion object
	{
		fun of(userId: Int): GetUserRoleRequest =
			object : GetUserRoleRequest
			{
				override val userId: Int = userId
				
				override fun equals(other: Any?): Boolean =
					isEqualTo(other)
				
				override fun hashCode(): Int =
					computeHashCode()
				
				override fun toString(): String =
					toJson()
			}
	}
}
