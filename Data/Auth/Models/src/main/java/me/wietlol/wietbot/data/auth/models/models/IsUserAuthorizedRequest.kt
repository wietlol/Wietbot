package me.wietlol.wietbot.data.auth.models.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.common.emptyHashCode
import me.wietlol.common.Jsonable
import me.wietlol.common.toJson
import me.wietlol.common.with
import java.util.*
import me.wietlol.wietbot.data.auth.models.serializers.IsUserAuthorizedRequestSerializer

interface IsUserAuthorizedRequest : BitSerializable, Jsonable
{
	override val serializationKey: UUID
		get() = IsUserAuthorizedRequestSerializer.modelId
	
	val userId: Int
	val permission: String
	val resource: String
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is IsUserAuthorizedRequest) return false
		
		if (userId != other.userId) return false
		if (permission != other.permission) return false
		if (resource != other.resource) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(userId)
			.with(permission)
			.with(resource)
	
	override fun toJson(): String =
		"""{"userId":${userId.toJson()},"permission":${permission.toJson()},"resource":${resource.toJson()}}"""
	
	companion object
	{
		fun of(userId: Int, permission: String, resource: String = "*"): IsUserAuthorizedRequest =
			object : IsUserAuthorizedRequest
			{
				override val userId: Int = userId
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
