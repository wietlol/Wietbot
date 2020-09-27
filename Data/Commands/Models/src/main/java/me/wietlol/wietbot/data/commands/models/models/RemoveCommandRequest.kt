// hash: #dea766ed
// data: serializationKey:eeb7218d-1d85-4ee0-b565-5dd17c63c9cf
// @formatter:off
package me.wietlol.wietbot.data.commands.models.models

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


interface RemoveCommandRequest : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("eeb7218d-1d85-4ee0-b565-5dd17c63c9cf")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val keyword: String
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is RemoveCommandRequest) return false
		
		if (keyword != other.keyword) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(keyword)
	
	override fun toJson(): String =
		"""{"keyword":${keyword.toJsonString()}}"""
	
	fun duplicate(): RemoveCommandRequest
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
