package me.wietlol.wietbot.commands.abbreviation.core.api

import com.amazonaws.services.lambda.runtime.events.SNSEvent
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import me.wietlol.aws.lambda.LambdaRequest
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.deserialize
import me.wietlol.wietbot.commands.abbreviation.core.interfaces.AbbreviationResolver
import me.wietlol.wietbot.commands.abbreviation.core.models.AbbreviationSearchResult
import me.wietlol.wietbot.commands.abbreviation.core.models.Definition
import me.wietlol.wietbot.commands.abbreviation.core.setup.DependencyInjection
import me.wietlol.wietbot.data.commands.models.models.CommandCall
import me.wietlol.wietbot.services.chatclient.models.ChatClientService
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequest
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequest.Companion
import org.koin.core.KoinComponent
import org.koin.core.get
import java.lang.StringBuilder

class AbbreviationHandler : KoinComponent
{
	init
	{
		DependencyInjection.bindServiceCollection()
	}
	
	val chatClient: ChatClientService = get()
	val mapper: ObjectMapper = get()
	val commandSchema: Schema = get()
	val abbreviationResolver: AbbreviationResolver = get()
	
	val maximumResults: Int = 5
	
	fun resolveSns(request: SNSEvent) =
		request
			.records
			?.singleOrNull()
			?.sns
			?.message
			?.let { mapper.readValue<LambdaRequest>(it) }
			?.payload
			?.let { commandSchema.deserialize<CommandCall>(it) }
			?.let { resolve(it) }
	
	private fun resolve(call: CommandCall)
	{
		val abbreviations = call.argumentText
			.split(" ")
			.filter { it.isNotEmpty() }
		
		when (abbreviations.size)
		{
			0 ->
			{
				chatClient.sendMessage(SendMessageRequest.of(
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
					chatClient.sendMessage(SendMessageRequest.of(
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
					
					chatClient.sendMessage(SendMessageRequest.of(
						call.message.source.id,
						textBuilder.toString()
					))
				}
			}
			else ->
			{
				chatClient.sendMessage(SendMessageRequest.of(
					call.message.source.id,
					"Dang it! I can't do it all at once!"
				))
			}
		}
	}
	
	private fun getRatingText(definition: Definition): String =
		", rated: ${(1..5).joinToString("") { if (it <= definition.rating) "★" else "☆" }}"
	
	private fun getTagText(definition: Definition): String =
		", tagged: ${definition.tags.joinToString()}"
}
