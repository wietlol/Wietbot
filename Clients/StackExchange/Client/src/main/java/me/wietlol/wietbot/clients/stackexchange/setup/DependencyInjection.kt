package me.wietlol.wietbot.clients.stackexchange.setup

import com.amazonaws.services.lambda.AWSLambdaClientBuilder
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder
import com.amazonaws.services.sns.AmazonSNSClientBuilder
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.core.BitBlock
import me.wietlol.bitblock.core.registry.LocalModelRegistry
import me.wietlol.bitblock.core.serialization.ImmutableSchema
import me.wietlol.konfig.api.Konfig
import me.wietlol.konfig.api.PathParser
import me.wietlol.konfig.api.get
import me.wietlol.konfig.aws.ssm.ParameterStoreDataSource
import me.wietlol.konfig.core.CommonKonfigBuilder
import me.wietlol.konfig.core.CommonPathParser
import me.wietlol.konfig.core.CommonSimpleValueResolver
import me.wietlol.konfig.core.CommonValueResolver
import me.wietlol.konfig.core.SimpleValueResolver
import me.wietlol.konfig.core.datasources.PropertiesDataSource
import me.wietlol.loggo.api.Logger
import me.wietlol.loggo.api.LoggerFactory
import me.wietlol.loggo.common.CommonLog
import me.wietlol.loggo.common.EventId
import me.wietlol.loggo.common.ScopedSequenceLogger
import me.wietlol.loggo.common.ScopedSourceLogger
import me.wietlol.loggo.common.information
import me.wietlol.loggo.common.jackson.CommonLogSerializer
import me.wietlol.loggo.common.logInformation
import me.wietlol.loggo.common.trace
import me.wietlol.loggo.common.warning
import me.wietlol.loggo.core.CachingLoggerFactory
import me.wietlol.loggo.core.ConverterLogger
import me.wietlol.loggo.core.FilteredLogger
import me.wietlol.loggo.core.GenericLoggerFactory
import me.wietlol.loggo.core.TriggerableLevelFilter
import me.wietlol.serialization.JacksonSerializerAdapter
import me.wietlol.serialization.SimpleJsonSerializer
import me.wietlol.wietbot.clients.stackexchange.util.LoggingWebSocketListener
import me.wietlol.wietbot.data.auth.client.AuthClient
import me.wietlol.wietbot.data.auth.models.AuthService
import me.wietlol.wietbot.data.commands.client.CommandClient
import me.wietlol.wietbot.data.commands.models.CommandService
import me.wietlol.wietbot.data.commands.models.WietbotDataCommands
import me.wietlol.wietbot.data.log.api.LogClient
import me.wietlol.wietbot.data.log.models.LogService
import me.wietlol.wietbot.data.log.models.models.SaveLogsRequest
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
import java.util.*

@Suppress("ComplexRedundantLet")
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
			factory {
				AWSSimpleSystemsManagementClientBuilder
//					.standard()
//					.withRegion(EU_WEST_1)
//					.build()
					.defaultClient()
			}
			factory {
				AWSLambdaClientBuilder
//					.standard()
//					.withRegion(EU_WEST_1)
//					.build()
					.defaultClient()
			}
			factory {
				AmazonSNSClientBuilder
//					.standard()
//					.withRegion(EU_WEST_1)
//					.build()
					.defaultClient()
			}
			
			single { buildKonfigPathParser() }
			single { buildKonfigSimpleValueResolver() }
			
			single { buildBaseLogger() }
			
			single { buildKonfig() }
			
			single { buildLoggerFactory() }
			
			single { buildLogClient() }
			single { buildAuthClient() }
			single { buildSerializer() }
			single { buildCommandClient() }
			single { buildCommandClientSchema() }
			
			single { buildChatClient() }
			single { CommonSeChatEvents() as SeChatEvents }
			factory { buildWebSocketListener() }
			single { buildSeWebSocketClientFactory() }
			single { buildSeWebSocketClient() }
		}
	
	private fun Scope.buildKonfig(): Konfig =
		CommonKonfigBuilder()
			.withPathParser(get())
			.withValueResolver(CommonValueResolver(
				get(),
				get()
			))
			.withLogger(get())
			.addSource(PropertiesDataSource(
				Properties().also { it.load(DependencyInjection.javaClass.getResourceAsStream("/application.properties")) },
				get()
			))
			.addSource(
				ParameterStoreDataSource(
					get(),
					"/me/wietlol/wietbot/stackexchange/",
					get()
				)
			)
			.build()
	
	private fun buildKonfigPathParser(): PathParser =
		CommonPathParser(".")
	
	private fun Scope.buildKonfigSimpleValueResolver(): SimpleValueResolver =
		CommonSimpleValueResolver(get())
	
	private fun Scope.buildAuthClient(): AuthService =
		AuthClient(get())
	
	private fun Scope.buildCommandClient(): CommandService =
		CommandClient(get())
	
	private fun buildCommandClientSchema(): Schema =
		LocalModelRegistry()
			.apply(BitBlock::initialize)
			.apply(WietbotDataCommands::initialize)
			.let { ImmutableSchema(WietbotDataCommands::class.java.getResourceAsStream("/me/wietlol/wietbot/data/commands/models/Api.bitschema"), it) }
	
	private fun Scope.buildChatClient(): ChatClientService =
		ChatClientClient(get())
	
	private fun buildSerializer(): SimpleJsonSerializer =
		ObjectMapper()
			.also { it.registerModule(KotlinModule()) }
			.also { mapper ->
				mapper.registerModule(
					SimpleModule()
						.apply { addDeserializer(BulkChatEvent::class.java, BulkChatEventDeserializer(mapper)) }
						.apply { addSerializer(CommonLog::class.java, CommonLogSerializer()) }
				)
			}
			.let { JacksonSerializerAdapter(it) }
	
	private val websocketMessageEventId = EventId(1814442332, "websocket-message")
	
	private fun Scope.buildWebSocketListener(): WebSocketListener
	{
		val logger: Logger<CommonLog> = get<Logger<CommonLog>>()
			.run {
				ScopedSourceLogger(this) { it + "websocket-messages" }
			}
		
		return NoOpWebSocketListener()
			.let {
				LoggingWebSocketListener(it) { message ->
					logger.logInformation(websocketMessageEventId, mapOf(
						"message" to message
					))
				}
			}
			.let { SeWebSocketListener(it, get(), get<SeChatEvents>().eventMap) }
	}
	
	private fun Scope.buildSeWebSocketClientFactory(): SeWebSocketClientFactory
	{
		val konfig: Konfig = get()
		
		val initialRoomId: Int = konfig.get("websocket.initialRoom") ?: 1
		
		return HttpSeWebSocketClientFactory(
			get(),
			get(),
			get(),
			initialRoomId,
			get()
		)
	}
	
	private fun Scope.buildSeWebSocketClient(): SeWebSocketClient
	{
		val konfig: Konfig = get()
		
		val credentials = konfig.get<SeCredentials>("credentials")
		
		return get<SeWebSocketClientFactory>().create(credentials)
	}
	
	private fun Scope.buildLoggerFactory(): LoggerFactory<CommonLog> =
		GenericLoggerFactory { buildLogger() }
			.let { CachingLoggerFactory(it) }
	
	private fun Scope.buildLogger(): Logger<CommonLog>
	{
		val konfig: Konfig = get<Konfig>().getSection("logger.triggerFilter")
		
		val filter = TriggerableLevelFilter<CommonLog>(
			konfig.getLogSeverity("startLevel") ?: information.value,
			konfig.getLogSeverity("triggerLevel") ?: warning.value,
			konfig.getLogSeverity("targetLevel") ?: trace.value
		)
		
		val scopeId = UUID.randomUUID()
		return get<Logger<CommonLog>>()
			.let { ScopedSequenceLogger(it) { scopeId } }
			.let { FilteredLogger(it, filter::apply) }
	}
	
	private fun Scope.buildBaseLogger(): Logger<CommonLog>
	{
		val serializer: SimpleJsonSerializer = get()
		val logService: LogService = get()
		
		return object : Logger<String>
		{
			override fun log(log: String)
			{
				logService.saveLogs(SaveLogsRequest.of(listOf(log)))
			}
			
			override fun close()
			{
				// nothing to do
			}
		}
			.let { ConverterLogger(it, serializer::serialize) }
			.let { logger -> ScopedSourceLogger(logger) { it + "wietbot-clients-stack-exchange" } }
	}

	private fun Scope.buildLogClient(): LogService =
		LogClient(get())
	
	private fun Konfig.getLogSeverity(path: String): Double? =
		get("$path.value")
			?: get<String?>("$path.name")?.toDouble()
}
