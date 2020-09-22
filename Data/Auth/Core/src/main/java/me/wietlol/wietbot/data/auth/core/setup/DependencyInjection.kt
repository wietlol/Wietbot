package me.wietlol.wietbot.data.auth.core.setup

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
import me.wietlol.wietbot.data.auth.core.interfaces.AuthRepository
import me.wietlol.wietbot.data.auth.core.repository.DatabaseProvider
import me.wietlol.wietbot.data.auth.core.repository.DatabaseSettings
import me.wietlol.wietbot.data.auth.core.repository.ExposedAuthRepository
import me.wietlol.wietbot.data.auth.models.WietbotDataAuth
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
			single { buildConfig() }
			single { buildConfigPathParser() }
			single { buildConfigSimpleValueResolver() }
			single { buildDatabase() }
			single { buildSerialization() }
			single<AuthRepository> { ExposedAuthRepository(get()) }
		}
	
	private fun buildSerialization(): Schema =
		BitSchemaBuilder.buildSchema(
			WietbotDataAuth::class.java.getResourceAsStream("/me/wietlol/wietbot/data/auth/models/Api.bitschema"),
			listOf(BitBlockBase, WietbotDataAuth),
		)
	
	private fun Scope.buildDatabase(): Database
	{
		val config: Konfig = get()
		
		val settings = config.get<DatabaseSettings>("database")
		
		return DatabaseProvider.getDatabase(settings)
	}
	
	private fun Scope.buildConfig(): Konfig =
		CommonKonfigBuilder()
			.withPathParser(get())
			.withValueResolver(CommonValueResolver(get(), get()))
			.addSource(EnvironmentDataSource(get()))
			.build()
	
	private fun buildConfigPathParser(): PathParser =
		CommonPathParser(".")
	
	private fun Scope.buildConfigSimpleValueResolver(): SimpleValueResolver =
		CommonSimpleValueResolver(get())
}
