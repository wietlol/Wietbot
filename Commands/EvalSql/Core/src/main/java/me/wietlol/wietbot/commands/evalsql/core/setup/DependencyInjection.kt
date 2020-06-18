package me.wietlol.wietbot.commands.evalsql.core.setup

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
import me.wietlol.wietbot.commands.evalsql.core.interfaces.ScriptEvaluator
import me.wietlol.wietbot.commands.evalsql.core.services.DatabaseSettings
import me.wietlol.wietbot.commands.evalsql.core.services.H2SqlScriptEvaluator
import me.wietlol.wietbot.data.commands.models.WietbotDataCommands
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
			modules(buildMainModule())
		}
	}
	
	private fun buildMainModule(): Module =
		module {
			single { buildJsonSerializer() }
			factory { AWSLambdaClientBuilder.defaultClient() }
			single { buildKonfig() }
			single(named("commandSchema")) { buildCommandSerialization() }
			single(named("chatClientSchema")) { buildChatClientSerialization() }
			single<ChatClientService> { ChatClientClient(get()) }
			single { buildScriptEvaluator() }
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
	
	private fun buildChatClientSerialization(): Schema =
		LocalModelRegistry()
			.apply(BitBlock::initialize)
			.apply(WietbotServicesChatClient::initialize)
			.let { ImmutableSchema(WietbotServicesChatClient::class.java.getResourceAsStream("/me/wietlol/wietbot/services/chatclient/models/Api.bitschema"), it) }
	
	private fun Scope.buildKonfig(): Konfig =
		CommonKonfigBuilder()
			.withPathParser(CommonPathParser("."))
			.withValueResolver(CommonValueResolver())
			.addSource(EnvironmentDataSource())
			.build()
	
	private fun Scope.buildScriptEvaluator(): ScriptEvaluator
	{
		val konfig: Konfig = get()
		
		val databaseSettings: DatabaseSettings = konfig.get("database")
		
		return H2SqlScriptEvaluator(databaseSettings)
	}
}
