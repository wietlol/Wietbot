package me.wietlol.wietbot.data.commands.core.setup

import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.core.BitBlockBase
import me.wietlol.bitblock.core.BitSchemaBuilder
import me.wietlol.konfig.api.Konfig
import me.wietlol.konfig.api.PathParser
import me.wietlol.konfig.api.get
import me.wietlol.konfig.core.CommonKonfigBuilder
import me.wietlol.konfig.core.CommonPathParser
import me.wietlol.konfig.core.CommonSimpleValueResolver
import me.wietlol.konfig.core.CommonValueResolver
import me.wietlol.konfig.core.SimpleValueResolver
import me.wietlol.konfig.core.datasources.EnvironmentDataSource
import me.wietlol.loggo.common.CommonLogger
import me.wietlol.loggo.core.loggers.DummyLogger
import me.wietlol.wietbot.data.commands.core.interfaces.CommandRepository
import me.wietlol.wietbot.data.commands.core.repository.DatabaseProvider
import me.wietlol.wietbot.data.commands.core.repository.DatabaseSettings
import me.wietlol.wietbot.data.commands.core.repository.ExposedCommandRepository
import me.wietlol.wietbot.data.commands.models.WietbotDataCommands
import me.wietlol.wietbot.libraries.lambdabase.dependencyinjection.setup.BaseDependencyInjection
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
			modules(BaseDependencyInjection.defaultServicesModules())
			modules(buildMainModule())
		}
	}
	
	private fun buildMainModule(): Module =
		module {
			single { buildDatabase() }
			single { buildSerialization() }
			single<CommandRepository> { ExposedCommandRepository(get()) }
		}
	
	private fun buildSerialization(): Schema =
		BitSchemaBuilder.buildSchema(
			WietbotDataCommands::class.java.getResourceAsStream("/me/wietlol/wietbot/data/commands/models/Api.bitschema"),
			listOf(BitBlockBase, WietbotDataCommands),
		)
	
	private fun Scope.buildDatabase(): Database
	{
		val config: Konfig = get()
		
		val settings = config.get<DatabaseSettings>("database")
		
		return DatabaseProvider.getDatabase(settings)
	}
}
