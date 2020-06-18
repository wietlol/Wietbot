package me.wietlol.wietbot.data.auth.models.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.common.emptyHashCode
import me.wietlol.common.Jsonable
import me.wietlol.common.toJson
import me.wietlol.common.with
import java.util.*
import me.wietlol.wietbot.data.auth.models.serializers.GetOrCreateUserResponseSerializer

interface GetOrCreateUserResponse : BitSerializable, Jsonable
{
	override val serializationKey: UUID
		get() = GetOrCreateUserResponseSerializer.modelId
	
	val user: User
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is GetOrCreateUserResponse) return false
		
		if (user != other.user) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(user)
	
	override fun toJson(): String =
		"""{"user":${user.toJson()}}"""
	
	companion object
	{
		fun of(user: User): GetOrCreateUserResponse =
			object : GetOrCreateUserResponse
			{
				override val user: User = user
				
				override fun equals(other: Any?): Boolean =
					isEqualTo(other)
				
				override fun hashCode(): Int =
					computeHashCode()
				
				override fun toString(): String =
					toJson()
			}
	}
}
