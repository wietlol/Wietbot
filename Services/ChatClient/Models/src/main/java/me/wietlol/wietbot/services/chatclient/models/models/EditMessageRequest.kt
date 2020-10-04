// hash: #5f1d6cc5
// data: serializationKey:d8200d78-b573-42a8-8152-88f227d477ba
// @formatter:off
package me.wietlol.wietbot.services.chatclient.models.models

import java.util.UUID
import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.utils.common.Jsonable
import me.wietlol.utils.common.emptyHashCode
import me.wietlol.utils.common.toJsonString
import me.wietlol.utils.common.with
import me.wietlol.wietbot.data.messages.models.models.Content

// @formatter:on
// @tomplot:customCode:start:gAeCSq
// @tomplot:customCode:end
// @formatter:off


interface EditMessageRequest : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("d8200d78-b573-42a8-8152-88f227d477ba")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val platform: String
	
	val messageId: String
	
	val content: Content
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is EditMessageRequest) return false
		
		if (platform != other.platform) return false
		if (messageId != other.messageId) return false
		if (content != other.content) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(platform)
			.with(messageId)
			.with(content)
	
	override fun toJson(): String =
		"""{"platform":${platform.toJsonString()},"messageId":${messageId.toJsonString()},"content":${content.toJsonString()}}"""
	
	fun duplicate(): EditMessageRequest
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
