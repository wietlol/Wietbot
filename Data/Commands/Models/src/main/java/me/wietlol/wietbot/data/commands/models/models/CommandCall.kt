// hash: #8b92dc41
// data: serializationKey:0998f281-c22d-4e36-a1e1-8371509cc240
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


interface CommandCall : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("0998f281-c22d-4e36-a1e1-8371509cc240")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val commandKeyword: String
	
	val argumentText: String
	
	val message: Message
	
	val options: Map<String, String>
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is CommandCall) return false
		
		if (commandKeyword != other.commandKeyword) return false
		if (argumentText != other.argumentText) return false
		if (message != other.message) return false
		if (options != other.options) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(commandKeyword)
			.with(argumentText)
			.with(message)
			.with(options)
	
	override fun toJson(): String =
		"""{"commandKeyword":${commandKeyword.toJsonString()},"argumentText":${argumentText.toJsonString()},"message":${message.toJsonString()},"options":${options.toJsonString()}}"""
	
	fun duplicate(): CommandCall
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
