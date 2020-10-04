// hash: #1a4a31b5
// data: serializationKey:64f7ac80-74f3-4bb3-9609-0cc50dba5ab1
// @formatter:off
package me.wietlol.wietbot.services.chatclient.models.models

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


interface SendMessageRetryRequest : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("64f7ac80-74f3-4bb3-9609-0cc50dba5ab1")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val request: SendMessageRequest
	
	val tryCount: Int
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is SendMessageRetryRequest) return false
		
		if (request != other.request) return false
		if (tryCount != other.tryCount) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(request)
			.with(tryCount)
	
	override fun toJson(): String =
		"""{"request":${request.toJsonString()},"tryCount":${tryCount.toJsonString()}}"""
	
	fun duplicate(): SendMessageRetryRequest
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
