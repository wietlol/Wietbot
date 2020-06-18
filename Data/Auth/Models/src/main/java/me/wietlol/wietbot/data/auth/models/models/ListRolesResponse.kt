package me.wietlol.wietbot.data.auth.models.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.common.emptyHashCode
import me.wietlol.common.Jsonable
import me.wietlol.common.toJson
import me.wietlol.common.with
import java.util.*
import me.wietlol.wietbot.data.auth.models.serializers.ListRolesResponseSerializer

interface ListRolesResponse : BitSerializable, Jsonable
{
	override val serializationKey: UUID
		get() = ListRolesResponseSerializer.modelId
	
	val roles: List<Role>
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is ListRolesResponse) return false
		
		if (roles != other.roles) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(roles)
	
	override fun toJson(): String =
		"""{"roles":${roles.toJson()}}"""
	
	companion object
	{
		fun of(roles: List<Role>): ListRolesResponse =
			object : ListRolesResponse
			{
				override val roles: List<Role> = roles
				
				override fun equals(other: Any?): Boolean =
					isEqualTo(other)
				
				override fun hashCode(): Int =
					computeHashCode()
				
				override fun toString(): String =
					toJson()
			}
	}
}
