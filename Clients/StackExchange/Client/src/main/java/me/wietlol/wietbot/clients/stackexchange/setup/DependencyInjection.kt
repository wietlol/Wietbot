package me.wietlol.wietbot.clients.stackexchange.setup

import com.amazonaws.regions.Regions.EU_WEST_1
import com.amazonaws.services.lambda.AWSLambdaClientBuilder
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder
import com.amazonaws.services.sns.AmazonSNSClientBuilder
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.core.BitBlockBase
import me.wietlol.bitblock.core.BitSchemaBuilder
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
import me.wietlol.loggo.core.GenericLoggerFactory
import me.wietlol.loggo.core.TriggerableLevelFilter
import me.wietlol.loggo.core.loggers.ConverterLogger
import me.wietlol.loggo.core.loggers.FilteredLogger
import me.wietlol.loggo.core.loggers.GenericLogger
import me.wietlol.utils.json.JacksonSerializerAdapter
import me.wietlol.utils.json.SimpleJsonSerializer
import me.wietlol.wietbot.clients.stackexchange.api.BitConnect
import me.wietlol.wietbot.clients.stackexchange.api.WietbotServer
import me.wietlol.wietbot.clients.stackexchange.botfeatures.BotFeature
import me.wietlol.wietbot.clients.stackexchange.botfeatures.ClientCommandHandler
import me.wietlol.wietbot.clients.stackexchange.botfeatures.SnsProxy
import me.wietlol.wietbot.clients.stackexchange.models.messages.WietbotClientsStackExchange
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.ClientCommandRequest
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.ClientCommandResponse
import me.wietlol.wietbot.clients.stackexchange.util.LoggingWebSocketListener
import me.wietlol.wietbot.data.log.api.LogClient
import me.wietlol.wietbot.data.log.models.LogService
import me.wietlol.wietbot.data.log.models.models.SaveLogsRequestImpl
import me.wietlol.wietbot.data.messages.models.WietbotDataMessages
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.SeCredentials
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.CommonSeChatEvents
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.HttpSeWebSocketClientFactory
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.NoOpWebSocketListener
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.SeChatEvents
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.SeWebSocketClientFactory
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.SeWebSocketListener
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.WebSocketListener
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.BulkChatEvent
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.serializers.BulkChatEventDeserializer
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
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
			single { buildSerializer() }
			single(named("clients-stack-exchange")) { buildStackExchangeApiSchema() }
			single(named("data-messages")) { buildBotMessageEventSchema() }

			single<SeChatEvents> { CommonSeChatEvents() }
			single { buildWebSocketListener() }
			single { buildSeWebSocketClientFactory() }
			
			single { buildWietbotServer() }
			single { buildBitConnect() }
			single { buildClientCommandHandler() }
			
			single { buildSnsProxy() }
		}
	
	private fun Scope.buildWietbotServer(): WietbotServer
	{
		val config = get<Konfig>()
		val port = config.get<Int>("server.port")
		return WietbotServer(get(), port)
	}
	
	private fun Scope.buildBitConnect(): BitConnect<ClientCommandRequest, ClientCommandResponse>
	{
		val schema: Schema = get(named("clients-stack-exchange"))
		val commandHandler: ClientCommandHandler = get()
		return BitConnect(schema, get(), commandHandler::process)
	}
	
	private fun Scope.buildClientCommandHandler(): ClientCommandHandler
	{
		val clientFactory: SeWebSocketClientFactory = get()
		val config: Konfig = get()
		val credentials: SeCredentials = config.get("credentials")
		val initialRoomId: Int = config.get("websocket.initialRoom") ?: 1
		return ClientCommandHandler(clientFactory, credentials, initialRoomId)
	}
	
	private fun Scope.buildKonfig(): Konfig =
		CommonKonfigBuilder()
			.withPathParser(get())
			.withValueResolver(CommonValueResolver(
				simpleValueResolver = get(),
				logger = get()
			))
			.withLogger(get())
			.addSource(PropertiesDataSource(
				properties = Properties().also { it.load(DependencyInjection.javaClass.getResourceAsStream("/application.properties")) },
				pathParser = get()
			))
			.addSource(
				ParameterStoreDataSource(
					ssmClient = get(),
					prefix = "/me/wietlol/wietbot/stackexchange/",
					pathParser = get()
				)
			)
			.build()
	
	private fun buildKonfigPathParser(): PathParser =
		CommonPathParser(".")
	
	private fun Scope.buildKonfigSimpleValueResolver(): SimpleValueResolver =
		CommonSimpleValueResolver(get())
	
	private fun buildStackExchangeApiSchema(): Schema =
		BitSchemaBuilder.buildSchema(
			WietbotClientsStackExchange::class.java.getResourceAsStream("/me/wietlol/wietbot/clients/stackexchange/models/SeChatMessages.bitschema"),
			listOf(BitBlockBase, WietbotClientsStackExchange),
		)
	
	private fun buildBotMessageEventSchema(): Schema =
		BitSchemaBuilder.buildSchema(
			WietbotDataMessages::class.java.getResourceAsStream("/me/wietlol/wietbot/data/messages/models/WietbotMessages.bitschema"),
			listOf(BitBlockBase, WietbotDataMessages),
		)
	
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
		val config: Konfig = get()
		
		val initialRoomId: Int = config.get("websocket.initialRoom") ?: 1
		val reconnectCheckInterval: Int = config.get("websocket.reconnectCheckInterval") ?: 300_000
		
		return HttpSeWebSocketClientFactory(
			listener = get(),
			chatEvents = get(),
			serializer = get(),
			initialRoom = initialRoomId,
			logger = get(),
			reconnectCheckInterval = reconnectCheckInterval.toLong()
		)
	}
	
	private fun Scope.buildLoggerFactory(): LoggerFactory<CommonLog> =
		GenericLoggerFactory { buildLogger() }
			.let { CachingLoggerFactory(it) }
	
	private fun Scope.buildLogger(): Logger<CommonLog>
	{
		val config: Konfig = get<Konfig>().getSection("logger.triggerFilter")
		
		val filter = TriggerableLevelFilter<CommonLog>(
			config.getLogSeverity("startLevel") ?: information.value,
			config.getLogSeverity("triggerLevel") ?: warning.value,
			config.getLogSeverity("targetLevel") ?: trace.value
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
		
		return GenericLogger<String>({ logService.saveLogs(SaveLogsRequestImpl(it.toList())) })
			.let { ConverterLogger(it, serializer::serialize) }
			.let { logger -> ScopedSourceLogger(logger) { it + "wietbot-clients-stack-exchange" } }
	}
	
	private fun Scope.buildLogClient(): LogService =
		LogClient(get())
	
	private fun Konfig.getLogSeverity(path: String): Double? =
		get("$path.value")
			?: get<String?>("$path.name")?.toDouble()
	
	private fun Scope.buildSnsProxy(): BotFeature
	{
		val config: Konfig = get()
		
		return SnsProxy(
			chatEvents = get(),
			snsClient = get(),
			serializer = get(),
			schema = get(named("data-messages")),
			logger = get(),
			stackOverflowMessageQueue = config.get("wietbot.stackOverflowMessageQueueArn"),
		)
	}
}
