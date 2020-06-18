package me.wietlol.wietbot.clients.stackexchange.botfeatures.commands

import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.RoomNotFoundException
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequest
import kotlin.streams.asStream
import kotlin.system.exitProcess

val internalCommands: List<BotCommand> = listOf(
	InternalCommand("listCommands") { call ->
		chatClient.sendMessage(SendMessageRequest.of(
			call.message.source.id,
			commands.joinToString { it.keyword }
		))
	},
	InternalCommand("join") { call ->
		val roomId = call.argumentText.toIntOrNull()
		
		try
		{
			if (roomId != null)
				webSocketClient.joinRoom(roomId)
			else
				chatClient.sendMessage(SendMessageRequest.of(
					call.message.source.id,
					"I cannot join rooms like that, I need proper numbers."
				))
		}
		catch (ex: RoomNotFoundException)
		{
			chatClient.sendMessage(SendMessageRequest.of(
				call.message.source.id,
				"That room does not exist."
			))
		}
	},
	InternalCommand("leave") { call ->
		val roomId = call.argumentText.toIntOrNull()
		
		try
		{
			if (roomId != null)
				webSocketClient.leaveRoom(roomId)
			else
				chatClient.sendMessage(SendMessageRequest.of(
					call.message.source.id,
					"I cannot leave rooms like that, I need proper numbers."
				))
		}
		catch (ex: RoomNotFoundException)
		{
			chatClient.sendMessage(SendMessageRequest.of(
				call.message.source.id,
				"That room does not exist."
			))
		}
	},
	InternalCommand("barrelRoll") { call ->
		val roomId = call.message.source.id

		(0..100)
			.asSequence()
			.asStream()
			.parallel()
			.forEach {
				webSocketClient.leaveRoom(roomId)
				webSocketClient.joinRoom(roomId)
			}
		webSocketClient.joinRoom(roomId)
	},
	InternalCommand("shutdown") { call ->
		webSocketClient.leaveAllRooms()
		webSocketClient.close()
		
		chatClient.sendMessage(SendMessageRequest.of(
			call.message.source.id,
			"laytar"
		))
		
		exitProcess(0)
	},
	InternalCommand("slowpoke") { call ->
		chatClient.sendMessage(SendMessageRequest.of(
			call.message.source.id,
			"https://i.imgur.com/EHh1nFw.png"
		))
	}
)
