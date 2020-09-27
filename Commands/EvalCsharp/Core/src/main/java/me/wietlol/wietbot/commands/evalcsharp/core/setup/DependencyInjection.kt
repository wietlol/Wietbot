package me.wietlol.wietbot.commands.evalcsharp.core.setup

import com.amazonaws.services.lambda.AWSLambdaClientBuilder
import com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES
import com.fasterxml.jackson.databind.ObjectMapper
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.core.BitBlockBase
import me.wietlol.bitblock.core.BitSchemaBuilder
import me.wietlol.wietbot.data.commands.models.WietbotDataCommands
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.setup.BaseDependencyInjection
import me.wietlol.wietbot.services.chatclient.client.ChatClientClient
import me.wietlol.wietbot.services.chatclient.models.ChatClientService
import me.wietlol.wietbot.services.chatclient.models.WietbotServicesChatClient
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

object DependencyInjection
{
	fun bindServiceCollection()
	{
		startKoin {
			modules(listOf(
				BaseDependencyInjection.defaultConfigurationServicesModule(),
				BaseDependencyInjection.defaultLoggingServicesModule(),
			))
			modules(buildMainModule())
		}
	}
	
	private fun buildMainModule(): Module =
		module {
			single { with(BaseDependencyInjection) { buildSimpleJsonSerializer() } }
			single { buildJsonSerializer() }
			
			factory { AWSLambdaClientBuilder.defaultClient() }
			single(named("commandSchema")) { buildCommandSerialization() }
			single(named("chatClientSchema")) { buildChatClientSerialization() }
			single<ChatClientService> { ChatClientClient(get()) }
		}
	
	private fun buildJsonSerializer(): ObjectMapper = BaseDependencyInjection.buildJsonMapper()
		.also { it.configure(ACCEPT_CASE_INSENSITIVE_PROPERTIES, true) }
	
	private fun buildCommandSerialization(): Schema =
		BitSchemaBuilder.buildSchema(
			WietbotDataCommands::class.java.getResourceAsStream("/me/wietlol/wietbot/data/commands/models/Api.bitschema"),
			listOf(BitBlockBase, WietbotDataCommands),
		)
	
	private fun buildChatClientSerialization(): Schema =
		BitSchemaBuilder.buildSchema(
			WietbotServicesChatClient::class.java.getResourceAsStream("/me/wietlol/wietbot/services/chatclient/models/Api.bitschema"),
			listOf(BitBlockBase, WietbotServicesChatClient),
		)
}
