package me.wietlol.wietbot.commands.hangman.core.api

import com.amazonaws.services.lambda.runtime.events.SNSEvent
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.loggo.api.Logger
import me.wietlol.loggo.common.CommonLog
import me.wietlol.loggo.common.EventId
import me.wietlol.utils.common.ByteWrapper
import me.wietlol.wietbot.commands.hangman.core.interfaces.HangmanMessageFormatter
import me.wietlol.wietbot.commands.hangman.core.interfaces.HangmanRepository
import me.wietlol.wietbot.commands.hangman.core.interfaces.HangmanService
import me.wietlol.wietbot.commands.hangman.core.interfaces.HangmanStateFactory
import me.wietlol.wietbot.commands.hangman.core.models.HangmanStateChange
import me.wietlol.wietbot.commands.hangman.core.models.HangmanStateChange.Type.duplicateGuess
import me.wietlol.wietbot.commands.hangman.core.setup.DependencyInjection
import me.wietlol.wietbot.data.commands.models.models.CommandCall
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.BaseHandler
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.FunctionEventIdSet
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.api.lambdaFunction
import me.wietlol.wietbot.services.chatclient.models.ChatClientService
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequestImpl
import org.koin.core.KoinComponent
import org.koin.core.get

class HangmanHandler : KoinComponent, BaseHandler
{
	init
	{
		DependencyInjection.bindServiceCollection()
	}
	
	private val repository: HangmanRepository = get()
	private val hangmanStateFactory: HangmanStateFactory = get()
	private val hangman: HangmanService = get()
	private val messageFormatter: HangmanMessageFormatter = get()
	private val chatClient: ChatClientService = get()
	private val mapper: ObjectMapper = get()
	
	override val requestSchema: Schema = get()
	override val responseSchema: Schema = requestSchema
	override val logger: Logger<CommonLog> = get()
	
	private val playEventIds = FunctionEventIdSet(
		EventId(1125870098, "play-request"),
		EventId(1360813113, "play-response"),
		EventId(1421485051, "play-error"),
	)
	
	fun playSns(request: SNSEvent) =
		request
			.records
			?.singleOrNull()
			?.sns
			?.message
			?.let { mapper.readValue<ByteWrapper>(it) }
			?.let { lambdaFunction(it, playEventIds, ::play) }
	
	private fun play(call: CommandCall): Int
	{
		val roomId = call.message.source.id
		
		val guess = call.argumentText
			.singleOrNull()
			?.toLowerCase()
		
		when
		{
			guess == null ->
			{
				chatClient.sendMessage(SendMessageRequestImpl(
					roomId,
					":${call.message.id} please provide a single character as your guess"
				))
			}
			hangman.isGuessValid(guess).not() ->
			{
				chatClient.sendMessage(SendMessageRequestImpl(
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
				
				chatClient.sendMessage(SendMessageRequestImpl(
					roomId,
					response
				))
			}
		}
		
		return 0
	}
}
