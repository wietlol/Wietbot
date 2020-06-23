package me.wietlol.wietbot.commands.hangman.core.setup

import com.amazonaws.services.lambda.AWSLambdaClientBuilder
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import me.wietlol.aws.lambda.LambdaException
import me.wietlol.aws.lambda.LambdaExceptionDeserializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.core.BitBlock
import me.wietlol.bitblock.core.registry.LocalModelRegistry
import me.wietlol.bitblock.core.serialization.ImmutableSchema
import me.wietlol.konfig.api.Konfig
import me.wietlol.konfig.api.get
import me.wietlol.konfig.core.CommonKonfigBuilder
import me.wietlol.konfig.core.CommonPathParser
import me.wietlol.konfig.core.CommonValueResolver
import me.wietlol.konfig.core.EnvironmentDataSource
import me.wietlol.wietbot.commands.hangman.core.interfaces.HangmanMessageFormatter
import me.wietlol.wietbot.commands.hangman.core.interfaces.HangmanRepository
import me.wietlol.wietbot.commands.hangman.core.interfaces.HangmanService
import me.wietlol.wietbot.commands.hangman.core.interfaces.HangmanStateFactory
import me.wietlol.wietbot.commands.hangman.core.repository.DatabaseProvider
import me.wietlol.wietbot.commands.hangman.core.repository.DatabaseSettings
import me.wietlol.wietbot.commands.hangman.core.repository.ExposedHangmanRepository
import me.wietlol.wietbot.commands.hangman.core.services.AsciiHangmanMessageFormatter
import me.wietlol.wietbot.commands.hangman.core.services.DbBackedHangmanStateFactory
import me.wietlol.wietbot.commands.hangman.core.services.HangmanGame
import me.wietlol.wietbot.commands.hangman.core.services.MonospaceHangmanMessageFormatter
import me.wietlol.wietbot.data.commands.models.WietbotDataCommands
import me.wietlol.wietbot.services.chatclient.client.ChatClientClient
import me.wietlol.wietbot.services.chatclient.models.ChatClientService
import org.jetbrains.exposed.sql.Database
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.scope.Scope
import org.koin.dsl.module

object DependencyInjection
{
	fun bindServiceCollection()
	{
		startKoin {
			modules(buildMainModule())
		}
	}

	private fun buildMainModule(): Module =
		module {
			single { buildJsonSerializer() }
			single { buildCommandSerialization() }
			single { buildKonfig() }
			single { buildDatabase() }
			single<HangmanRepository> { ExposedHangmanRepository(get()) }
			single { buildStateFactory() }
			single { buildHangmanService() }
			single { buildMessageFormatter() }
			factory { AWSLambdaClientBuilder.defaultClient() }
			single<ChatClientService> { ChatClientClient(get()) }
		}
	
	private fun buildJsonSerializer(): ObjectMapper = ObjectMapper()
		.also { mapper ->
			mapper.registerModule(
				SimpleModule()
					.apply { addDeserializer(LambdaException::class.java, LambdaExceptionDeserializer(mapper)) }
			)
		}
	
	private fun buildCommandSerialization(): Schema =
		LocalModelRegistry()
			.apply(BitBlock::initialize)
			.apply(WietbotDataCommands::initialize)
			.let { ImmutableSchema(WietbotDataCommands::class.java.getResourceAsStream("/me/wietlol/wietbot/data/commands/models/Api.bitschema"), it) }
	
	private fun buildKonfig(): Konfig =
		CommonKonfigBuilder()
			.withPathParser(CommonPathParser("."))
			.withValueResolver(CommonValueResolver())
			.addSource(EnvironmentDataSource())
			.build()
	
	private fun Scope.buildDatabase(): Database
	{
		val konfig: Konfig = get()
		
		val settings = konfig.get<DatabaseSettings>("database")
		
		return DatabaseProvider.getDatabase(settings)
	}
	
	private fun Scope.buildStateFactory(): HangmanStateFactory =
		DbBackedHangmanStateFactory(get())
	
	private fun buildHangmanService(): HangmanService =
		HangmanGame()
	
	private fun buildMessageFormatter(): HangmanMessageFormatter =
		AsciiHangmanMessageFormatter()
			.let { MonospaceHangmanMessageFormatter(it) }
}
