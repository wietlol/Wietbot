package me.wietlol.wietbot.data.commands.core.api

import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.loggo.api.Logger
import me.wietlol.loggo.common.CommonLog
import me.wietlol.loggo.common.EventId
import me.wietlol.utils.common.ByteWrapper
import me.wietlol.wietbot.data.commands.core.interfaces.CommandRepository
import me.wietlol.wietbot.data.commands.core.setup.DependencyInjection
import me.wietlol.wietbot.data.commands.models.CommandService
import me.wietlol.wietbot.data.commands.models.models.CommandImpl
import me.wietlol.wietbot.data.commands.models.models.CreateCommandRequest
import me.wietlol.wietbot.data.commands.models.models.CreateCommandResponse
import me.wietlol.wietbot.data.commands.models.models.CreateCommandResponseImpl
import me.wietlol.wietbot.data.commands.models.models.ListCommandsRequest
import me.wietlol.wietbot.data.commands.models.models.ListCommandsResponse
import me.wietlol.wietbot.data.commands.models.models.ListCommandsResponseImpl
import me.wietlol.wietbot.data.commands.models.models.RemoveCommandRequest
import me.wietlol.wietbot.data.commands.models.models.RemoveCommandResponse
import me.wietlol.wietbot.data.commands.models.models.RemoveCommandResponseImpl
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.BaseHandler
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.FunctionEventIdSet
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.lambdaFunction
import org.koin.core.KoinComponent
import org.koin.core.get

class CommandHandler : KoinComponent, CommandService, BaseHandler
{
	init
	{
		DependencyInjection.bindServiceCollection()
	}
	
	val commandRepository: CommandRepository = get()
	
	override val requestSchema: Schema = get()
	override val responseSchema: Schema = requestSchema
	override val logger: Logger<CommonLog> = get()
	
	private val createCommandEventIds = FunctionEventIdSet(
		EventId(1874078135, "createCommand-request"),
		EventId(1360375690, "createCommand-response"),
		EventId(1755754800, "createCommand-error"),
	)
	private val removeCommandEventIds = FunctionEventIdSet(
		EventId(2063209037, "removeCommand-request"),
		EventId(1530162331, "removeCommand-response"),
		EventId(1502114527, "removeCommand-error"),
	)
	private val listCommandsEventIds = FunctionEventIdSet(
		EventId(1798414243, "listCommands-request"),
		EventId(1974924639, "listCommands-response"),
		EventId(1975704279, "listCommands-error"),
	)
	
	fun createCommandBit(request: ByteWrapper): ByteWrapper? =
		lambdaFunction(request, createCommandEventIds, ::createCommand)
	
	override fun createCommand(request: CreateCommandRequest): CreateCommandResponse
	{
		commandRepository.createCommand(request.keyword, request.listenerQueue)
		
		return CreateCommandResponseImpl()
	}
	
	fun removeCommandBit(request: ByteWrapper): ByteWrapper? =
		lambdaFunction(request, removeCommandEventIds, ::removeCommand)
	
	override fun removeCommand(request: RemoveCommandRequest): RemoveCommandResponse
	{
		commandRepository.removeCommand(request.keyword)
		
		return RemoveCommandResponseImpl()
	}
	
	fun listCommandsBit(request: ByteWrapper): ByteWrapper? =
		lambdaFunction(request, listCommandsEventIds, ::listCommands)
	
	override fun listCommands(request: ListCommandsRequest): ListCommandsResponse
	{
		val commands = commandRepository.listCommands()
		
		return ListCommandsResponseImpl(
			commands.map { CommandImpl(it.keyword, it.listenerQueue) }
		)
	}
}
