package me.wietlol.wietbot.data.auth.models.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.common.emptyHashCode
import me.wietlol.common.Jsonable
import me.wietlol.common.toJson
import me.wietlol.common.with
import java.util.*
import me.wietlol.wietbot.data.auth.models.serializers.CreateRevokedAuthorityResponseSerializer

interface CreateRevokedAuthorityResponse : BitSerializable, Jsonable
{
	override val serializationKey: UUID
		get() = CreateRevokedAuthorityResponseSerializer.modelId
	
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is CreateRevokedAuthorityResponse) return false
		
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
	
	override fun toJson(): String =
		"""{}"""
	
	companion object
	{
		fun of(): CreateRevokedAuthorityResponse =
			object : CreateRevokedAuthorityResponse
			{
				
				override fun equals(other: Any?): Boolean =
					isEqualTo(other)
				
				override fun hashCode(): Int =
					computeHashCode()
				
				override fun toString(): String =
					toJson()
			}
	}
}
