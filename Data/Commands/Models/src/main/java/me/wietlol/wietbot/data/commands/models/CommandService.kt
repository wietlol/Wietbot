package me.wietlol.wietbot.data.commands.models

import me.wietlol.wietbot.data.commands.models.models.CreateCommandRequest
import me.wietlol.wietbot.data.commands.models.models.CreateCommandResponse
import me.wietlol.wietbot.data.commands.models.models.ListCommandsRequest
import me.wietlol.wietbot.data.commands.models.models.ListCommandsResponse
import me.wietlol.wietbot.data.commands.models.models.RemoveCommandRequest
import me.wietlol.wietbot.data.commands.models.models.RemoveCommandResponse

interface CommandService
{
	fun createCommand(request: CreateCommandRequest): CreateCommandResponse
	
	fun removeCommand(request: RemoveCommandRequest): RemoveCommandResponse
	
	fun listCommands(request: ListCommandsRequest): ListCommandsResponse
}
