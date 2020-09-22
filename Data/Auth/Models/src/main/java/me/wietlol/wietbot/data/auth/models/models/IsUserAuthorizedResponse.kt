// hash: #3103e15f
// data: serializationKey:43a03df4-3791-47f4-877f-56a28e7057db
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


interface IsUserAuthorizedResponse : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("43a03df4-3791-47f4-877f-56a28e7057db")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val isAuthorized: Boolean
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is IsUserAuthorizedResponse) return false
		
		if (isAuthorized != other.isAuthorized) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(isAuthorized)
	
	override fun toJson(): String =
		"""{"isAuthorized":${isAuthorized.toJsonString()}}"""
	
	fun duplicate(): IsUserAuthorizedResponse
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
