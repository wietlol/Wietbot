package me.wietlol.wietbot.commands.abbreviation.core.api

import com.amazonaws.services.lambda.runtime.events.SNSEvent
import com.fasterxml.jackson.databind.ObjectMapper
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.loggo.api.Logger
import me.wietlol.loggo.common.CommonLog
import me.wietlol.loggo.common.EventId
import me.wietlol.wietbot.commands.abbreviation.core.interfaces.AbbreviationResolver
import me.wietlol.wietbot.commands.abbreviation.core.models.Definition
import me.wietlol.wietbot.commands.abbreviation.core.setup.DependencyInjection
import me.wietlol.wietbot.data.commands.models.models.CommandCall
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.BaseEventHandler
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.FunctionEventIdSet
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.lambdaFunction
import me.wietlol.wietbot.services.chatclient.models.ChatClientService
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequestImpl
import org.koin.core.KoinComponent
import org.koin.core.get
import java.lang.StringBuilder

class AbbreviationHandler : KoinComponent, BaseEventHandler
{
	init
	{
		DependencyInjection.bindServiceCollection()
	}
	
	private val chatClient: ChatClientService = get()
	private val abbreviationResolver: AbbreviationResolver = get()
	
	private val maximumResults: Int = 5
	
	override val requestSchema: Schema = get()
	override val responseSchema: Schema = requestSchema
	override val logger: Logger<CommonLog> = get()
	override val mapper: ObjectMapper = get()
	
	private val resolveEventIds = FunctionEventIdSet(
		EventId(1844626768, "resolve-request"),
		EventId(1356687980, "resolve-response"),
		EventId(1670115860, "resolve-error"),
	)
	
	fun resolveSns(request: SNSEvent) =
		lambdaFunction(request, resolveEventIds, ::resolve)
	
	private fun resolve(call: CommandCall): Int
	{
		val abbreviations = call.argumentText
			.split(" ")
			.filter { it.isNotEmpty() }
		
		when (abbreviations.size)
		{
			0 ->
			{
				chatClient.sendMessage(SendMessageRequestImpl(
					call.message.source.id,
					"I need at least one abbreviation to search for."
				))
			}
			1 ->
			{
				val abbreviation = abbreviations.single()
				val (definitions, suggestions) = abbreviationResolver.findDefinitions(abbreviation)
				
				if (definitions.isEmpty())
				{
					chatClient.sendMessage(SendMessageRequestImpl(
						call.message.source.id,
						"No definitions found for '$abbreviation', did you mean any of the following: ${suggestions.joinToString(", ")}."
					))
				}
				else
				{
					val textBuilder = StringBuilder()
					definitions
						.sortedByDescending { it.rating }
						.take(maximumResults)
						.map { "${it.description}${getRatingText(it)}${getTagText(it)}" }
						.forEach { textBuilder.append(it).append("\n") }
					
					if (definitions.size > maximumResults)
						textBuilder.append("${definitions.size - maximumResults} more results available at https://www.abbreviations.com/$abbreviation\n")
					
					textBuilder.append("powered by https://www.abbreviations.com/")
					
					chatClient.sendMessage(SendMessageRequestImpl(
						call.message.source.id,
						textBuilder.toString()
					))
				}
			}
			else ->
			{
				chatClient.sendMessage(SendMessageRequestImpl(
					call.message.source.id,
					"Dang it! I can't do it all at once!"
				))
			}
		}

		return 0
	}
	
	private fun getRatingText(definition: Definition): String =
		", rated: ${(1..5).joinToString("") { if (it <= definition.rating) "★" else "☆" }}"
	
	private fun getTagText(definition: Definition): String =
		", tagged: ${definition.tags.joinToString()}"
}
