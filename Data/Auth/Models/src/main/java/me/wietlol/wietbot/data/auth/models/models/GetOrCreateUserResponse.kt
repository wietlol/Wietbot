// hash: #e0ebec98
// data: serializationKey:48ba8a71-95ce-49eb-b9c0-2076a76d3790
// @formatter:off
package me.wietlol.wietbot.data.auth.models.models

import java.util.UUID
import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.utils.common.Jsonable
import me.wietlol.utils.common.emptyHashCode
import me.wietlol.utils.common.toJsonString
import me.wietlol.utils.common.with

// @formatter:on
// @tomplot:customCode:start:gAeCSq
// @tomplot:customCode:end
// @formatter:off


interface GetOrCreateUserResponse : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("48ba8a71-95ce-49eb-b9c0-2076a76d3790")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
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
		"""{"user":${user.toJsonString()}}"""
	
	fun duplicate(): GetOrCreateUserResponse
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
