package me.wietlol.wietbot.commands.hangman.core.api

import com.amazonaws.services.lambda.runtime.events.SNSEvent
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import me.wietlol.aws.lambda.LambdaRequest
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.deserialize
import me.wietlol.wietbot.commands.hangman.core.interfaces.HangmanMessageFormatter
import me.wietlol.wietbot.commands.hangman.core.interfaces.HangmanRepository
import me.wietlol.wietbot.commands.hangman.core.interfaces.HangmanService
import me.wietlol.wietbot.commands.hangman.core.interfaces.HangmanStateFactory
import me.wietlol.wietbot.commands.hangman.core.models.HangmanStateChange
import me.wietlol.wietbot.commands.hangman.core.models.HangmanStateChange.Type.duplicateGuess
import me.wietlol.wietbot.commands.hangman.core.setup.DependencyInjection
import me.wietlol.wietbot.data.commands.models.models.CommandCall
import me.wietlol.wietbot.services.chatclient.models.ChatClientService
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequest
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequest.Companion
import org.koin.core.KoinComponent
import org.koin.core.get
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HangmanHandler : KoinComponent
{
	init
	{
		DependencyInjection.bindServiceCollection()
	}
	
	val repository: HangmanRepository = get()
	val hangmanStateFactory: HangmanStateFactory = get()
	val hangman: HangmanService = get()
	val messageFormatter: HangmanMessageFormatter = get()
	val chatClient: ChatClientService = get()
	val mapper: ObjectMapper = get()
	val commandSchema: Schema = get()
	
	fun playSns(request: SNSEvent) =
		request
			.records
			?.singleOrNull()
			?.sns
			?.message
			?.let { mapper.readValue<LambdaRequest>(it) }
			?.payload
			?.let { commandSchema.deserialize<CommandCall>(it) }
			?.let { play(it) }
	
	
	private fun play(call: CommandCall)
	{
		val roomId = call.message.source.id
		
		val guess = call.argumentText
			.singleOrNull()
			?.toLowerCase()
		
		when
		{
			guess == null ->
			{
				chatClient.sendMessage(SendMessageRequest.of(
					roomId,
					":${call.message.id} please provide a single character as your guess"
				))
			}
			hangman.isGuessValid(guess).not() ->
			{
				chatClient.sendMessage(Companion.of(
					roomId,
					":${call.message.id} please use an english alphabet character as your guess"
				))
			}
			else ->
			{
				val gameId = "so-chat-$roomId"
				
				val state = repository.getLatestState(gameId)
					?: hangmanStateFactory.create()
						.also { repository.insertState(gameId, it) }
				
				val stateChange: HangmanStateChange = hangman.guessCharacter(state, guess)
				
				if (stateChange.type != duplicateGuess)
					repository.insertState(gameId, stateChange.newState)
				
				if (stateChange.hasEnded())
					repository.archive(gameId)
				
				val response = messageFormatter.formatMessage(stateChange)
				
				chatClient.sendMessage(SendMessageRequest.of(
					roomId,
					response
				))
			}
		}
	}
}
