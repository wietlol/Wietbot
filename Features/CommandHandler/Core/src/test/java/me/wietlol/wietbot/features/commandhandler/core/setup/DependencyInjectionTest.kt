package me.wietlol.wietbot.features.commandhandler.core.setup

import me.wietlol.bitblock.api.serialization.Schema
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.qualifier.named

class DependencyInjectionTest : KoinComponent
{
	init
	{
		DependencyInjection.bindServiceCollection()
	}
	
	@Test
	fun foo()
	{
		val commandSchema: Schema = get(named("data-commands"))
		
		assertThat(commandSchema).isNotNull
	}
}
