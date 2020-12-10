// hash: #350896c6
// data: serializationKey:4e83066f-87e4-442a-853c-dea9b60d7eb8
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


interface User : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("4e83066f-87e4-442a-853c-dea9b60d7eb8")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val localId: String
	
	val localName: String
	
	val platform: Platform
	
	val internalId: Long
	
	val stackExchangeId: String
	
	val stackExchangeName: String
	
	val discordId: String
	
	val discordName: String
	
	val wietbotWebsiteId: String
	
	val wietbotWebsiteName: String
	
	val role: Int
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is User) return false
		
		if (localId != other.localId) return false
		if (localName != other.localName) return false
		if (platform != other.platform) return false
		if (internalId != other.internalId) return false
		if (stackExchangeId != other.stackExchangeId) return false
		if (stackExchangeName != other.stackExchangeName) return false
		if (discordId != other.discordId) return false
		if (discordName != other.discordName) return false
		if (wietbotWebsiteId != other.wietbotWebsiteId) return false
		if (wietbotWebsiteName != other.wietbotWebsiteName) return false
		if (role != other.role) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(localId)
			.with(localName)
			.with(platform)
			.with(internalId)
			.with(stackExchangeId)
			.with(stackExchangeName)
			.with(discordId)
			.with(discordName)
			.with(wietbotWebsiteId)
			.with(wietbotWebsiteName)
			.with(role)
	
	override fun toJson(): String =
		"""{"localId":${localId.toJsonString()},"localName":${localName.toJsonString()},"platform":${platform.toJsonString()},"internalId":${internalId.toJsonString()},"stackExchangeId":${stackExchangeId.toJsonString()},"stackExchangeName":${stackExchangeName.toJsonString()},"discordId":${discordId.toJsonString()},"discordName":${discordName.toJsonString()},"wietbotWebsiteId":${wietbotWebsiteId.toJsonString()},"wietbotWebsiteName":${wietbotWebsiteName.toJsonString()},"role":${role.toJsonString()}}"""
	
	fun duplicate(): User
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
