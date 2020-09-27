package me.wietlol.wietbot.commands.evalsql.core.setup

import com.amazonaws.services.lambda.AWSLambdaClientBuilder
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.core.BitBlockBase
import me.wietlol.bitblock.core.BitSchemaBuilder
import me.wietlol.konfig.api.Konfig
import me.wietlol.konfig.api.get
import me.wietlol.wietbot.commands.evalsql.core.interfaces.ScriptEvaluator
import me.wietlol.wietbot.commands.evalsql.core.services.DatabaseSettings
import me.wietlol.wietbot.commands.evalsql.core.services.H2SqlScriptEvaluator
import me.wietlol.wietbot.data.commands.models.WietbotDataCommands
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.setup.BaseDependencyInjection
import me.wietlol.wietbot.services.chatclient.client.ChatClientClient
import me.wietlol.wietbot.services.chatclient.models.ChatClientService
import me.wietlol.wietbot.services.chatclient.models.WietbotServicesChatClient
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
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
			factory { AWSLambdaClientBuilder.defaultClient() }
			single(named("commandSchema")) { buildCommandSerialization() }
			single(named("chatClientSchema")) { buildChatClientSerialization() }
			single<ChatClientService> { ChatClientClient(get()) }
			single { buildScriptEvaluator() }
		}
	
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
	
	private fun Scope.buildScriptEvaluator(): ScriptEvaluator
	{
		val config: Konfig = get()
		
		val databaseSettings: DatabaseSettings = config.get("database")
		
		return H2SqlScriptEvaluator(databaseSettings)
	}
}
