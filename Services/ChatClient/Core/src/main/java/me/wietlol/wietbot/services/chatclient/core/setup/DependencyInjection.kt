package me.wietlol.wietbot.services.chatclient.core.setup

import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.AmazonSNSClientBuilder
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.core.BitBlockBase
import me.wietlol.bitblock.core.BitSchemaBuilder
import me.wietlol.konfig.api.Konfig
import me.wietlol.konfig.api.get
import me.wietlol.loggo.core.loggers.DummyLogger
import me.wietlol.utils.json.SimpleJsonSerializer
import me.wietlol.wietbot.data.messages.models.WietbotDataMessages
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.setup.BaseDependencyInjection
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.HttpSeChatClientFactory
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.SeChatClient
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.SeChatClientFactory
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.SeCredentials
import me.wietlol.wietbot.services.chatclient.core.interfaces.ChatRetryService
import me.wietlol.wietbot.services.chatclient.core.interfaces.PlatformResolver
import me.wietlol.wietbot.services.chatclient.core.services.HashPlatformResolver
import me.wietlol.wietbot.services.chatclient.core.services.SnsChatRetryService
import me.wietlol.wietbot.services.chatclient.core.services.StackOverflowPlatform
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
			single { buildSnsClient() }
			single { buildSerialization() }
			single { buildSeCredentials() }
			single { buildChatClientFactory() }
			single { buildChatClient() }
			factory { buildChatRetryService() }
			factory { buildPlatformResolver() }
		}
	
	private fun buildSnsClient(): AmazonSNS =
		AmazonSNSClientBuilder.defaultClient()
	
	private fun buildSerialization(): Schema =
		BitSchemaBuilder.buildSchema(
			WietbotServicesChatClient::class.java.getResourceAsStream("/me/wietlol/wietbot/services/chatclient/models/Api.bitschema"),
			listOf(BitBlockBase, WietbotServicesChatClient, WietbotDataMessages),
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
	
	private fun Scope.buildChatClient(): SeChatClient
	{
		val factory: SeChatClientFactory = get()
		
		return factory.create(get())
	}
	
	private fun Scope.buildChatRetryService(): ChatRetryService
	{
		val config: Konfig = get()
		
		return SnsChatRetryService(
			snsClient = get(),
			schema = get(),
			json = get(),
			topicBaseArn = config.get("retryBaseSnsTopic")
		)
	}
	
	private fun Scope.buildPlatformResolver(): PlatformResolver =
		HashPlatformResolver(
			mapOf(
				"stack-overflow" to { StackOverflowPlatform(get()) }
			)
		)
}
