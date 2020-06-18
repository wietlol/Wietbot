package me.wietlol.wietbot.data.commands.core.api

import me.wietlol.aws.lambda.LambdaRequest
import me.wietlol.aws.lambda.LambdaResponse
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.deserialize
import me.wietlol.wietbot.data.commands.core.interfaces.CommandRepository
import me.wietlol.wietbot.data.commands.core.setup.DependencyInjection
import me.wietlol.wietbot.data.commands.models.CommandService
import me.wietlol.wietbot.data.commands.models.models.Command
import me.wietlol.wietbot.data.commands.models.models.CreateCommandRequest
import me.wietlol.wietbot.data.commands.models.models.CreateCommandResponse
import me.wietlol.wietbot.data.commands.models.models.ListCommandsRequest
import me.wietlol.wietbot.data.commands.models.models.ListCommandsResponse
import me.wietlol.wietbot.data.commands.models.models.RemoveCommandRequest
import me.wietlol.wietbot.data.commands.models.models.RemoveCommandResponse
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject

class CommandHandler : KoinComponent, CommandService
{
	init
	{
		DependencyInjection.bindServiceCollection()
	}
	
	val commandRepository: CommandRepository = get()
	val schema: Schema = get()
	
	fun createCommandBit(request: LambdaRequest): LambdaResponse? =
		request
			.payload
			?.let { schema.deserialize<CreateCommandRequest>(it) }
			?.let { createCommand(it) }
			?.let { schema.serialize(it) }
			?.let { LambdaResponse(it) }
	
	override fun createCommand(request: CreateCommandRequest): CreateCommandResponse
	{
		commandRepository.createCommand(request.keyword, request.listenerQueue)
		
		return CreateCommandResponse.of()
	}
	
	fun removeCommandBit(request: LambdaRequest): LambdaResponse? =
		request
			.payload
			?.let { schema.deserialize<RemoveCommandRequest>(it) }
			?.let { removeCommand(it) }
			?.let { schema.serialize(it) }
			?.let { LambdaResponse(it) }
	
	override fun removeCommand(request: RemoveCommandRequest): RemoveCommandResponse
	{
		commandRepository.removeCommand(request.keyword)
		
		return RemoveCommandResponse.of()
	}
	
	fun listCommandsBit(request: LambdaRequest): LambdaResponse? =
		request
			.payload
			?.let { schema.deserialize<ListCommandsRequest>(it) }
			?.let { listCommands(it) }
			?.let { schema.serialize(it) }
			?.let { LambdaResponse(it) }
	
	override fun listCommands(request: ListCommandsRequest): ListCommandsResponse
	{
		val commands = commandRepository.listCommands()
		
		return ListCommandsResponse.of(
			commands.map { Command.of(it.keyword, it.listenerQueue) }
		)
	}
}
