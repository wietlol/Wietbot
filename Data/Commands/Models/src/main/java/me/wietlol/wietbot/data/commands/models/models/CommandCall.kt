package me.wietlol.wietbot.data.commands.models.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.common.emptyHashCode
import me.wietlol.common.Jsonable
import me.wietlol.common.toJson
import me.wietlol.common.with
import java.util.*
import me.wietlol.wietbot.data.commands.models.serializers.CommandCallSerializer

interface CommandCall : BitSerializable, Jsonable
{
	override val serializationKey: UUID
		get() = CommandCallSerializer.modelId
	
	val commandKeyword: String
	val argumentText: String
	val message: Message
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is CommandCall) return false
		
		if (commandKeyword != other.commandKeyword) return false
		if (argumentText != other.argumentText) return false
		if (message != other.message) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(commandKeyword)
			.with(argumentText)
			.with(message)
	
	override fun toJson(): String =
		"""{"commandKeyword":${commandKeyword.toJson()},"argumentText":${argumentText.toJson()},"message":${message.toJson()}}"""
	
	companion object
	{
		fun of(commandKeyword: String, argumentText: String, message: Message): CommandCall =
			object : CommandCall
			{
				override val commandKeyword: String = commandKeyword
				override val argumentText: String = argumentText
				override val message: Message = message
				
				override fun equals(other: Any?): Boolean =
					isEqualTo(other)
				
				override fun hashCode(): Int =
					computeHashCode()
				
				override fun toString(): String =
					toJson()
			}
	}
}
