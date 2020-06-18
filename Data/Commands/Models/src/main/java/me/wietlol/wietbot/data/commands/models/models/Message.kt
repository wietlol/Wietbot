package me.wietlol.wietbot.data.commands.models.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.common.emptyHashCode
import me.wietlol.common.Jsonable
import me.wietlol.common.toJson
import me.wietlol.common.with
import java.util.*
import me.wietlol.wietbot.data.commands.models.serializers.MessageSerializer

interface Message : BitSerializable, Jsonable
{
	override val serializationKey: UUID
		get() = MessageSerializer.modelId
	
	val id: Int
	val sender: User
	val fullText: String
	val source: Room
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is Message) return false
		
		if (id != other.id) return false
		if (sender != other.sender) return false
		if (fullText != other.fullText) return false
		if (source != other.source) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(id)
			.with(sender)
			.with(fullText)
			.with(source)
	
	override fun toJson(): String =
		"""{"id":${id.toJson()},"sender":${sender.toJson()},"fullText":${fullText.toJson()},"source":${source.toJson()}}"""
	
	companion object
	{
		fun of(id: Int, sender: User, fullText: String, source: Room): Message =
			object : Message
			{
				override val id: Int = id
				override val sender: User = sender
				override val fullText: String = fullText
				override val source: Room = source
				
				override fun equals(other: Any?): Boolean =
					isEqualTo(other)
				
				override fun hashCode(): Int =
					computeHashCode()
				
				override fun toString(): String =
					toJson()
			}
	}
}
