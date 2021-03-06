// hash: #6402acc2
// @formatter:off
package me.wietlol.wietbot.clients.stackexchange.models.messages.builders

import me.wietlol.wietbot.clients.stackexchange.models.messages.models.*
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.GetInfoResponseImpl

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class GetInfoResponseBuilder
{
	var name: String?
		= null
	
	var architecture: String?
		= null
	
	var version: String?
		= null
	
	var processors: Int?
		= null
	
	var usedMemory: Long?
		= null
	
	var maxMemory: Long?
		= null
	
	var runtimeSince: Long?
		= null
	
	var clientIsRunning: Boolean?
		= null
	
	var clientSince: Long?
		= null
	
	fun build(): GetInfoResponse =
		GetInfoResponseImpl(
			name!!,
			architecture!!,
			version!!,
			processors!!,
			usedMemory!!,
			maxMemory!!,
			runtimeSince!!,
			clientIsRunning!!,
			clientSince!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
