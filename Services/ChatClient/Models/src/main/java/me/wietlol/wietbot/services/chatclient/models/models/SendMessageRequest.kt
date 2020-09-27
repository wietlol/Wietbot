// hash: #a60faefb
// data: serializationKey:dcb9cb6e-7ac3-4b65-8a6c-3f64d74e982e
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


interface SendMessageRequest : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("dcb9cb6e-7ac3-4b65-8a6c-3f64d74e982e")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val roomId: Int
	
	val text: String
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is SendMessageRequest) return false
		
		if (roomId != other.roomId) return false
		if (text != other.text) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(roomId)
			.with(text)
	
	override fun toJson(): String =
		"""{"roomId":${roomId.toJsonString()},"text":${text.toJsonString()}}"""
	
	fun duplicate(): SendMessageRequest
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
