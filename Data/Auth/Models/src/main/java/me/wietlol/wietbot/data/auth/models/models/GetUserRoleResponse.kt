package me.wietlol.wietbot.data.auth.models.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.common.emptyHashCode
import me.wietlol.common.Jsonable
import me.wietlol.common.toJson
import me.wietlol.common.with
import java.util.*
import me.wietlol.wietbot.data.auth.models.serializers.GetUserRoleResponseSerializer

interface GetUserRoleResponse : BitSerializable, Jsonable
{
	override val serializationKey: UUID
		get() = GetUserRoleResponseSerializer.modelId
	
	val role: Role
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is GetUserRoleResponse) return false
		
		if (role != other.role) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(role)
	
	override fun toJson(): String =
		"""{"role":${role.toJson()}}"""
	
	companion object
	{
		fun of(role: Role): GetUserRoleResponse =
			object : GetUserRoleResponse
			{
				override val role: Role = role
				
				override fun equals(other: Any?): Boolean =
					isEqualTo(other)
				
				override fun hashCode(): Int =
					computeHashCode()
				
				override fun toString(): String =
					toJson()
			}
	}
}
