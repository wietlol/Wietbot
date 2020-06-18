package me.wietlol.wietbot.data.auth.models.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.common.emptyHashCode
import me.wietlol.common.Jsonable
import me.wietlol.common.toJson
import me.wietlol.common.with
import java.util.*
import me.wietlol.wietbot.data.auth.models.serializers.ListPermissionsResponseSerializer

interface ListPermissionsResponse : BitSerializable, Jsonable
{
	override val serializationKey: UUID
		get() = ListPermissionsResponseSerializer.modelId
	
	val isAuthorized: List<Permission>
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is ListPermissionsResponse) return false
		
		if (isAuthorized != other.isAuthorized) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(isAuthorized)
	
	override fun toJson(): String =
		"""{"isAuthorized":${isAuthorized.toJson()}}"""
	
	companion object
	{
		fun of(isAuthorized: List<Permission>): ListPermissionsResponse =
			object : ListPermissionsResponse
			{
				override val isAuthorized: List<Permission> = isAuthorized
				
				override fun equals(other: Any?): Boolean =
					isEqualTo(other)
				
				override fun hashCode(): Int =
					computeHashCode()
				
				override fun toString(): String =
					toJson()
			}
	}
}
