package me.wietlol.wietbot.services.chatclient.core.setup

import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.core.BitBlockBase
import me.wietlol.bitblock.core.BitSchemaBuilder
import me.wietlol.konfig.api.Konfig
import me.wietlol.konfig.api.get
import me.wietlol.loggo.core.loggers.DummyLogger
import me.wietlol.utils.json.SimpleJsonSerializer
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.setup.BaseDependencyInjection
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.HttpSeChatClientFactory
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.SeChatClientFactory
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.SeCredentials
import me.wietlol.wietbot.services.chatclient.models.WietbotServicesChatClient
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
			single { buildSerialization() }
			single { buildSeCredentials() }
			single { buildChatClientFactory() }
		}
	
	private fun buildSerialization(): Schema =
		BitSchemaBuilder.buildSchema(
			WietbotServicesChatClient::class.java.getResourceAsStream("/me/wietlol/wietbot/services/chatclient/models/Api.bitschema"),
			listOf(BitBlockBase, WietbotServicesChatClient),
		)
	
	private fun Scope.buildSeCredentials(): SeCredentials
	{
		val config: Konfig = get()
		
		return config.get("seCredentials")
	}
	
	private fun Scope.buildChatClientFactory(): SeChatClientFactory
	{
		val serializer: SimpleJsonSerializer = get()
		
		return HttpSeChatClientFactory(serializer, DummyLogger())
	}
}
