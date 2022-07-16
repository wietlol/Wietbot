package me.wietlol.wietbot.features.commandhandler.core.setup

import com.amazonaws.services.lambda.AWSLambdaClientBuilder
import com.amazonaws.services.sns.AmazonSNSClientBuilder
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.core.BitBlockBase
import me.wietlol.bitblock.core.BitSchemaBuilder
import me.wietlol.konfig.api.Konfig
import me.wietlol.konfig.api.get
import me.wietlol.wietbot.data.auth.client.AuthClient
import me.wietlol.wietbot.data.auth.models.AuthService
import me.wietlol.wietbot.data.commands.client.CommandClient
import me.wietlol.wietbot.data.commands.models.CommandService
import me.wietlol.wietbot.data.commands.models.WietbotDataCommands
import me.wietlol.wietbot.data.messages.models.WietbotDataMessages
import me.wietlol.wietbot.features.commandhandler.core.interfaces.CommandCallParser
import me.wietlol.wietbot.features.commandhandler.core.interfaces.MessageFilter
import me.wietlol.wietbot.features.commandhandler.core.services.CommandExecutor
import me.wietlol.wietbot.features.commandhandler.core.services.FullPassMessageFilter
import me.wietlol.wietbot.features.commandhandler.core.services.MultiMessageFilter
import me.wietlol.wietbot.features.commandhandler.core.services.PlatformSwitchMessageFilter
import me.wietlol.wietbot.features.commandhandler.core.services.PrefixMessageFilter
import me.wietlol.wietbot.features.commandhandler.core.services.RegexCommandCallParser
import me.wietlol.wietbot.features.commandhandler.core.services.SenderBlacklistMessageFilter
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.setup.BaseDependencyInjection
import me.wietlol.wietbot.services.chatclient.client.ChatClientClient
import me.wietlol.wietbot.services.chatclient.models.ChatClientService
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
			factory { AmazonSNSClientBuilder.defaultClient() }
			single { buildCommandService() }
			single { buildAuthService() }
			single { buildChatClientService() }
			single { buildCommandCallParser() }
			single { buildCommandExecutor() }
			single { buildMessageFilter() }
			single(named("data-messages")) { buildBotMessageEventSchema() }
			single(named("data-commands")) { buildBotCommandSchema() }
		}
	
	private fun buildBotMessageEventSchema(): Schema =
		BitSchemaBuilder.buildSchema(
			WietbotDataMessages::class.java.getResourceAsStream("/me/wietlol/wietbot/data/messages/models/WietbotMessages.bitschema"),
			listOf(BitBlockBase, WietbotDataMessages),
		)
	
	private fun buildBotCommandSchema(): Schema =
		BitSchemaBuilder.buildSchema(
			WietbotDataCommands::class.java.getResourceAsStream("/me/wietlol/wietbot/data/commands/models/Api.bitschema"),
			listOf(BitBlockBase, WietbotDataCommands, WietbotDataMessages),
		)
	
	private fun Scope.buildCommandCallParser(): CommandCallParser
	{
		val config: Konfig = get()
		
		val stackOverflowPrefix: String = config.get<String>("commandExecute.prefix").toLowerCase()
		
		return RegexCommandCallParser(mapOf(
			"stack-overflow" to stackOverflowPrefix,
		))
	}
	
	private fun Scope.buildCommandService(): CommandService =
		CommandClient(
			lambdaClient = get(),
			serializer = get(named("data-commands")),
		)
	
	private fun Scope.buildAuthService(): AuthService =
		AuthClient(
			lambdaClient = get()
		)
	
	private fun Scope.buildChatClientService(): ChatClientService =
		ChatClientClient(
			lambdaClient = get()
		)
	
	private fun Scope.buildCommandExecutor(): CommandExecutor =
		CommandExecutor(
			snsClient = get(),
			serializer = get(),
			konfig = get(),
			commandService = get(),
			authService = get(),
			chatClient = get(),
			schema = get(named("data-commands")),
			commandCallParser = get(),
			logger = get(),
			messageFilter = get(),
		)
	
	private fun Scope.buildMessageFilter(): MessageFilter
	{
		val config: Konfig = get()
		
		val wietbotStackOverflowId: String = config.get("wietbot.id")
		val stackOverflowPrefix: String = config.get("commandExecute.prefix")
		
		return PlatformSwitchMessageFilter(mapOf(
			"stack-overflow" to MultiMessageFilter(listOf(
				SenderBlacklistMessageFilter(listOf(wietbotStackOverflowId)),
				PrefixMessageFilter(stackOverflowPrefix)
			)),
			"sandbox" to FullPassMessageFilter(),
		))
	}
}
