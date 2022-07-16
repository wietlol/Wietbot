package me.wietlol.wietbot.features.commandhandler.core.api

import com.amazonaws.services.lambda.runtime.events.SNSEvent
import com.fasterxml.jackson.databind.ObjectMapper
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.loggo.api.Logger
import me.wietlol.loggo.common.CommonLog
import me.wietlol.loggo.common.EventId
import me.wietlol.wietbot.data.messages.models.models.ChatEvent
import me.wietlol.wietbot.data.messages.models.models.MessageEvent
import me.wietlol.wietbot.data.messages.models.models.MessageEventList
import me.wietlol.wietbot.features.commandhandler.core.services.CommandExecutor
import me.wietlol.wietbot.features.commandhandler.core.setup.DependencyInjection
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.BaseEventHandler
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.FunctionEventIdSet
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.lambdaFunction
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.qualifier.named

class CommandHandlerHandler : KoinComponent, BaseEventHandler
{
	init
	{
		DependencyInjection.bindServiceCollection()
	}
	
	private val commandExecutor: CommandExecutor = get()
	
	override val requestSchema: Schema = get(named("data-messages"))
	override val responseSchema: Schema = requestSchema
	override val logger: Logger<CommonLog> = get()
	override val mapper: ObjectMapper = get()
	
	private val handlecommandEventIds = FunctionEventIdSet(
		EventId(1002740363, "handleCommand-request"),
		EventId(1601591067, "handleCommand-response"),
		EventId(1680660639, "handleCommand-error"),
	)
	
	fun handleCommandSns(request: SNSEvent) =
		lambdaFunction(request, handlecommandEventIds, ::handleCommand)
	
	private fun handleCommand(it: MessageEventList): Int
	{
		if (it.events.last() is MessageEvent) // skips deletes, only passes creates and edits
			commandExecutor.invoke(it)
		return 0
	}
}
