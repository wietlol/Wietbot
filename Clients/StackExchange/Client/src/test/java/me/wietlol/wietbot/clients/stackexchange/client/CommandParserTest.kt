package me.wietlol.wietbot.clients.stackexchange.client

import me.wietlol.wietbot.data.commands.models.models.CommandCall
import me.wietlol.wietbot.data.commands.models.models.Message
import me.wietlol.wietbot.data.commands.models.models.Room
import me.wietlol.wietbot.data.commands.models.models.User
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.MessageEvent
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.MessagePosted

object CommandParserTest
{
	@JvmStatic
	fun main(args: Array<String>)
	{
		val text = "@Wietbot test"
		
		val command = parseCommand(MessagePosted(
			1,
			text,
			1,
			1,
			"Wietlol",
			1,
			"Sandbox",
			1,
			null,
			null
		))
		
		println(command)
	}
	
	private fun parseCommand(event: MessageEvent): CommandCall?
	{
		val pattern = "^@Wietbot ([a-zA-Z0-9_-]+) ?(?s)(.*)\$".toRegex()
		
		val match = pattern.matchEntire(event.content) ?: return null
		
		return CommandCall.of(
			match.groupValues[1],
			match.groupValues[2],
			Message.of(
				event.id,
				User.of(event.userId, event.userName),
				event.content,
				Room.of(event.roomId)
			)
		)
	}
}
