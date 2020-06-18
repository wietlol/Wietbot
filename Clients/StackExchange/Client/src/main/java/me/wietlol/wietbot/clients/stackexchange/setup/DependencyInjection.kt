package me.wietlol.wietbot.clients.stackexchange.setup

import com.amazonaws.services.lambda.AWSLambdaClientBuilder
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder
import com.amazonaws.services.sns.AmazonSNSClientBuilder
import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.core.BitBlock
import me.wietlol.bitblock.core.registry.LocalModelRegistry
import me.wietlol.bitblock.core.serialization.ImmutableSchema
import me.wietlol.konfig.api.Konfig
import me.wietlol.konfig.api.get
import me.wietlol.konfig.aws.ssm.ParameterStoreDataSource
import me.wietlol.konfig.core.CommonKonfigBuilder
import me.wietlol.konfig.core.CommonPathParser
import me.wietlol.konfig.core.CommonValueResolver
import me.wietlol.konfig.core.PropertiesDataSource
import me.wietlol.serialization.JacksonSerializerAdapter
import me.wietlol.serialization.JsonSerializer2
import me.wietlol.wietbot.clients.stackexchange.util.LoggingWebSocketListener
import me.wietlol.wietbot.data.auth.client.AuthClient
import me.wietlol.wietbot.data.auth.models.AuthService
import me.wietlol.wietbot.data.commands.client.CommandClient
import me.wietlol.wietbot.data.commands.models.CommandService
import me.wietlol.wietbot.data.commands.models.WietbotDataCommands
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.SeCredentials
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.CommonSeChatEvents
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.HttpSeWebSocketClientFactory
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.NoOpWebSocketListener
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.SeChatEvents
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.SeWebSocketClient
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.SeWebSocketClientFactory
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.SeWebSocketListener
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.WebSocketListener
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.BulkChatEvent
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.serializers.BulkChatEventDeserializer
import me.wietlol.wietbot.services.chatclient.client.ChatClientClient
import me.wietlol.wietbot.services.chatclient.models.ChatClientService
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.scope.Scope
import org.koin.dsl.module
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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
			factory { AWSSimpleSystemsManagementClientBuilder.defaultClient() }
			factory { AWSLambdaClientBuilder.defaultClient() }
			factory { AmazonSQSClientBuilder.defaultClient() }
			factory { AmazonSNSClientBuilder.defaultClient() }
			single { buildKonfig() }
			single { buildAuthClient() }
			single { buildSerializer() }
			single { buildCommandClient() }
			single { buildCommandClientSchema() }
			single { buildChatClient() }
			single { CommonSeChatEvents() as SeChatEvents }
			factory { buildWebSocketListener() }
			single { HttpSeWebSocketClientFactory(get(), get(), get()) as SeWebSocketClientFactory }
			single { buildSeWebSocketClient() }
		}
	
	private fun Scope.buildKonfig(): Konfig =
		CommonKonfigBuilder()
			.withPathParser(CommonPathParser("."))
			.withValueResolver(CommonValueResolver())
			.addSource(PropertiesDataSource(Properties().also { it.load(DependencyInjection.javaClass.getResourceAsStream("/application.properties")) }))
			.addSource(ParameterStoreDataSource(get(), "/me/wietlol/wietbot/stackexchange/"))
			.build()
	
	private fun Scope.buildAuthClient(): AuthService =
		AuthClient(get())
	
	private fun Scope.buildCommandClient(): CommandService =
		CommandClient(get())
	
	private fun Scope.buildCommandClientSchema(): Schema =
		LocalModelRegistry()
			.apply(BitBlock::initialize)
			.apply(WietbotDataCommands::initialize)
			.let { ImmutableSchema(WietbotDataCommands::class.java.getResourceAsStream("/me/wietlol/wietbot/data/commands/models/Api.bitschema"), it) }
	
	private fun Scope.buildChatClient(): ChatClientService =
		ChatClientClient(get())
	
	private fun buildSerializer(): JsonSerializer2 =
		ObjectMapper()
			.also { it.registerModule(KotlinModule()) }
			.also { mapper ->
				mapper.registerModule(
					SimpleModule()
						.apply { addDeserializer(BulkChatEvent::class.java, BulkChatEventDeserializer(mapper)) }
				)
			}
			.let { JacksonSerializerAdapter(it) }
	
	private fun Scope.buildWebSocketListener(): WebSocketListener =
		NoOpWebSocketListener()
			.let { LoggingWebSocketListener(it) { message ->
				val now = LocalDateTime.now()
				val date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
				val time = now.format(DateTimeFormatter.ofPattern("HH-mm"))
				File("/log/messages/$date/$time.log")
					.also { it.parentFile.mkdirs() }
					.also { it.createNewFile() }
					.appendText(message + "\n")
			} }
			.let { SeWebSocketListener(it, get(), get<SeChatEvents>().eventMap) }
	
	private fun Scope.buildSeWebSocketClient(): SeWebSocketClient
	{
		val konfig: Konfig = get()
		
		val credentials = konfig.get<SeCredentials>("credentials")
		
		return get<SeWebSocketClientFactory>().create(credentials)
	}
}
