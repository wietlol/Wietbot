// hash: #edae8d45
// data: serializationKey:d310fcea-e2b4-424b-a4b1-6dda202550d5
// @formatter:off
package me.wietlol.wietbot.clients.stackexchange.models.messages.models

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


interface GetInfoResponse : BitSerializable, ClientCommandResponse, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("d310fcea-e2b4-424b-a4b1-6dda202550d5")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val name: String
	
	val architecture: String
	
	val version: String
	
	val processors: Int
	
	val usedMemory: Long
	
	val maxMemory: Long
	
	val runtimeSince: Long
	
	val clientIsRunning: Boolean
	
	val clientSince: Long
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is GetInfoResponse) return false
		
		if (name != other.name) return false
		if (architecture != other.architecture) return false
		if (version != other.version) return false
		if (processors != other.processors) return false
		if (usedMemory != other.usedMemory) return false
		if (maxMemory != other.maxMemory) return false
		if (runtimeSince != other.runtimeSince) return false
		if (clientIsRunning != other.clientIsRunning) return false
		if (clientSince != other.clientSince) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(name)
			.with(architecture)
			.with(version)
			.with(processors)
			.with(usedMemory)
			.with(maxMemory)
			.with(runtimeSince)
			.with(clientIsRunning)
			.with(clientSince)
	
	override fun toJson(): String =
		"""{"name":${name.toJsonString()},"architecture":${architecture.toJsonString()},"version":${version.toJsonString()},"processors":${processors.toJsonString()},"usedMemory":${usedMemory.toJsonString()},"maxMemory":${maxMemory.toJsonString()},"runtimeSince":${runtimeSince.toJsonString()},"clientIsRunning":${clientIsRunning.toJsonString()},"clientSince":${clientSince.toJsonString()}}"""
	
	override fun duplicate(): GetInfoResponse
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
