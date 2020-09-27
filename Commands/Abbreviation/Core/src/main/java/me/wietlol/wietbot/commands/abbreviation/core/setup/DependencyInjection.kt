package me.wietlol.wietbot.commands.abbreviation.core.setup

import com.amazonaws.services.lambda.AWSLambdaClientBuilder
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.core.BitBlockBase
import me.wietlol.bitblock.core.BitSchemaBuilder
import me.wietlol.wietbot.commands.abbreviation.core.interfaces.AbbreviationResolver
import me.wietlol.wietbot.commands.abbreviation.core.services.AbbreviationsHtmlScraper
import me.wietlol.wietbot.data.commands.models.WietbotDataCommands
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.setup.BaseDependencyInjection
import me.wietlol.wietbot.services.chatclient.client.ChatClientClient
import me.wietlol.wietbot.services.chatclient.models.ChatClientService
import org.koin.core.context.startKoin
import org.koin.core.module.Module
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
			factory { AWSLambdaClientBuilder.defaultClient() }
			single { buildCommandSerialization() }
			single { buildAbbreviationResolver() }
			single<ChatClientService> { ChatClientClient(get()) }
		}
	
	private fun buildCommandSerialization(): Schema =
		BitSchemaBuilder.buildSchema(
			WietbotDataCommands::class.java.getResourceAsStream("/me/wietlol/wietbot/data/commands/models/Api.bitschema"),
			listOf(BitBlockBase, WietbotDataCommands),
		)
	
	private fun buildAbbreviationResolver(): AbbreviationResolver =
		AbbreviationsHtmlScraper()
}
