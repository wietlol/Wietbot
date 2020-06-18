package me.wietlol.wietbot.data.commands.models.builders

import me.wietlol.wietbot.data.commands.models.models.*
import java.util.*

class RoomBuilder
{
	var id: Int? = null
	
	fun build(): Room =
		Room.of(
			id!!
		)
}
