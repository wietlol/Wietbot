// hash: #78bcfd50
// data: serializationKey:b207d3c9-0cdf-4785-afba-7d8308be8497
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


interface EditMessageRetryRequest : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("b207d3c9-0cdf-4785-afba-7d8308be8497")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val request: EditMessageRequest
	
	val tryCount: Int
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is EditMessageRetryRequest) return false
		
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
	
	fun duplicate(): EditMessageRetryRequest
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
