// hash: #0bebc143
// @formatter:off
package me.wietlol.wietbot.clients.stackexchange.models.messages.models

import me.wietlol.wietbot.clients.stackexchange.models.messages.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off


data class GetInfoResponseImpl(
	override val name: String,
	override val architecture: String,
	override val version: String,
	override val processors: Int,
	override val usedMemory: Long,
	override val maxMemory: Long,
	override val runtimeSince: Long,
	override val clientIsRunning: Boolean,
	override val clientSince: Long,
) : GetInfoResponse
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): GetInfoResponseImpl =
		copy(
			name = name,
			architecture = architecture,
			version = version,
			processors = processors,
			usedMemory = usedMemory,
			maxMemory = maxMemory,
			runtimeSince = runtimeSince,
			clientIsRunning = clientIsRunning,
			clientSince = clientSince,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
