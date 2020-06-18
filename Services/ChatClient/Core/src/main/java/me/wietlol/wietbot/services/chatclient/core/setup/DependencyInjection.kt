package me.wietlol.wietbot.services.chatclient.core.setup

import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.AmazonSNSClientBuilder
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
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
import me.wietlol.konfig.core.SimpleValueResolver
import me.wietlol.serialization.JacksonSerializerAdapter
import me.wietlol.serialization.JsonSerializer2
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.HttpSeChatClientFactory
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.SeChatClient
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.SeCredentials
import me.wietlol.wietbot.services.chatclient.core.interfaces.ChatRetryService
import me.wietlol.wietbot.services.chatclient.core.services.SnsChatRetryService
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
			modules(buildMainModule())
		}
	}
	
	private fun buildMainModule(): Module =
		module {
			single { buildKonfig() }
			single { buildJsonSerializer() }
			single { buildSerialization() }
			single { buildChatClient() }
			factory { buildSnsClient() }
			factory { buildChatRetryService() }
		}
	
	private fun buildJsonSerializer(): JsonSerializer2 = ObjectMapper()
		.also { it.registerModule(KotlinModule()) }
		.let { JacksonSerializerAdapter(it) }
	
	private fun buildSerialization(): Schema =
		LocalModelRegistry()
			.apply(BitBlock::initialize)
			.apply(WietbotServicesChatClient::initialize)
			.let { ImmutableSchema(WietbotServicesChatClient::class.java.getResourceAsStream("/me/wietlol/wietbot/services/chatclient/models/Api.bitschema"), it) }
	
	private fun Scope.buildKonfig(): Konfig =
		CommonKonfigBuilder()
			.withPathParser(CommonPathParser("."))
			.withValueResolver(CommonValueResolver(SimpleValueResolver()))
			.addSource(EnvironmentDataSource())
			.build()
	
	private fun Scope.buildChatClient(): SeChatClient
	{
		val konfig: Konfig = get()
		
		val credentials: SeCredentials = konfig.get("seCredentials")
		
		val serializer: JsonSerializer2 = get()
		
		return HttpSeChatClientFactory(serializer, logger = { println(it) })
			.create(credentials)
	}
	
	private fun buildSnsClient(): AmazonSNS =
		AmazonSNSClientBuilder.defaultClient()
	
	private fun Scope.buildChatRetryService(): ChatRetryService
	{
		val config: Konfig = get()
		
		return SnsChatRetryService(get(), get(), config.get("retryBaseSnsTopic"))
	}
}
