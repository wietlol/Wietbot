package me.wietlol.wietbot.data.auth.models.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.common.emptyHashCode
import me.wietlol.common.Jsonable
import me.wietlol.common.toJson
import me.wietlol.common.with
import java.util.*
import me.wietlol.wietbot.data.auth.models.serializers.CreatePermissionRequestSerializer

interface CreatePermissionRequest : BitSerializable, Jsonable
{
	override val serializationKey: UUID
		get() = CreatePermissionRequestSerializer.modelId
	
	val name: String
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is CreatePermissionRequest) return false
		
		if (name != other.name) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(name)
	
	override fun toJson(): String =
		"""{"name":${name.toJson()}}"""
	
	companion object
	{
		fun of(name: String): CreatePermissionRequest =
			object : CreatePermissionRequest
			{
				override val name: String = name
				
				override fun equals(other: Any?): Boolean =
					isEqualTo(other)
				
				override fun hashCode(): Int =
					computeHashCode()
				
				override fun toString(): String =
					toJson()
			}
	}
}
