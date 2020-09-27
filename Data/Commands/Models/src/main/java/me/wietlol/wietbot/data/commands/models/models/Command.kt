// hash: #d82c479f
// data: serializationKey:cb121e37-e4de-479f-bbc9-0b8380a2b94c
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


interface Command : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("cb121e37-e4de-479f-bbc9-0b8380a2b94c")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val keyword: String
	
	val listenerQueue: String
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is Command) return false
		
		if (keyword != other.keyword) return false
		if (listenerQueue != other.listenerQueue) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(keyword)
			.with(listenerQueue)
	
	override fun toJson(): String =
		"""{"keyword":${keyword.toJsonString()},"listenerQueue":${listenerQueue.toJsonString()}}"""
	
	fun duplicate(): Command
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
