package me.wietlol.wietbot.data.commands.core.setup

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder
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
import me.wietlol.wietbot.data.commands.core.interfaces.CommandRepository
import me.wietlol.wietbot.data.commands.core.repository.DatabaseProvider
import me.wietlol.wietbot.data.commands.core.repository.DatabaseSettings
import me.wietlol.wietbot.data.commands.core.repository.ExposedCommandRepository
import me.wietlol.wietbot.data.commands.models.WietbotDataCommands
import org.jetbrains.exposed.sql.Database
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
			factory { AWSSimpleSystemsManagementClientBuilder.defaultClient() }
			single { buildKonfig() }
			single { buildDatabase() }
			single { buildSerialization() }
			single { ExposedCommandRepository(get()) as CommandRepository }
		}
	
	private fun buildSerialization(): Schema =
		LocalModelRegistry()
			.apply(BitBlock::initialize)
			.apply(WietbotDataCommands::initialize)
			.let { ImmutableSchema(WietbotDataCommands::class.java.getResourceAsStream("/me/wietlol/wietbot/data/commands/models/Api.bitschema"), it) }
	
	private fun Scope.buildDatabase(): Database
	{
		val konfig: Konfig = get()
		
		val settings = konfig.get<DatabaseSettings>("database")
		
		return DatabaseProvider.getDatabase(settings)
	}
	
	private fun Scope.buildKonfig(): Konfig =
		CommonKonfigBuilder()
			.withPathParser(CommonPathParser("."))
			.withValueResolver(CommonValueResolver())
			.addSource(EnvironmentDataSource())
			.build()
}
