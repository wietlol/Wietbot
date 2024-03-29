// hash: #6902c4dc
// data: serializationKey:d18267a5-4694-4c9b-9a1a-8bc69a09dd25
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


interface GetOrCreateUserRequest : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("d18267a5-4694-4c9b-9a1a-8bc69a09dd25")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val localId: String
	
	val localName: String
	
	val platform: Platform
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is GetOrCreateUserRequest) return false
		
		if (localId != other.localId) return false
		if (localName != other.localName) return false
		if (platform != other.platform) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(localId)
			.with(localName)
			.with(platform)
	
	override fun toJson(): String =
		"""{"localId":${localId.toJsonString()},"localName":${localName.toJsonString()},"platform":${platform.toJsonString()}}"""
	
	fun duplicate(): GetOrCreateUserRequest
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
