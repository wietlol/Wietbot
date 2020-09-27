package me.wietlol.wietbot.commands.hangman.core.setup

import com.amazonaws.services.lambda.AWSLambdaClientBuilder
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.core.BitBlockBase
import me.wietlol.bitblock.core.BitSchemaBuilder
import me.wietlol.konfig.api.Konfig
import me.wietlol.konfig.api.get
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
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.setup.BaseDependencyInjection
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
			modules(BaseDependencyInjection.defaultServicesModules())
			modules(buildMainModule())
		}
	}

	private fun buildMainModule(): Module =
		module {
			single { buildCommandSerialization() }
			single { buildDatabase() }
			single<HangmanRepository> { ExposedHangmanRepository(get()) }
			single { buildStateFactory() }
			single { buildHangmanService() }
			single { buildMessageFormatter() }
			factory { AWSLambdaClientBuilder.defaultClient() }
			single<ChatClientService> { ChatClientClient(get()) }
		}
	
	private fun buildCommandSerialization(): Schema =
		BitSchemaBuilder.buildSchema(
			WietbotDataCommands::class.java.getResourceAsStream("/me/wietlol/wietbot/data/commands/models/Api.bitschema"),
			listOf(BitBlockBase, WietbotDataCommands),
		)
	
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
