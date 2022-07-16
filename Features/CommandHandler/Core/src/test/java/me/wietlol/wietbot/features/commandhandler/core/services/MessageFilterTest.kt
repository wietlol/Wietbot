package me.wietlol.wietbot.features.commandhandler.core.services

import com.github.stefanbirkner.systemlambda.SystemLambda.withEnvironmentVariable
import me.wietlol.konfig.api.Konfig
import me.wietlol.wietbot.data.messages.models.dsl.ContentBuilder
import me.wietlol.wietbot.data.messages.models.models.ChatUserImpl
import me.wietlol.wietbot.data.messages.models.models.MessageEvent
import me.wietlol.wietbot.data.messages.models.models.MessagePostedEventImpl
import me.wietlol.wietbot.data.messages.models.models.MessageSourceImpl
import me.wietlol.wietbot.features.commandhandler.core.interfaces.MessageFilter
import me.wietlol.wietbot.features.commandhandler.core.setup.DependencyInjection
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Test
import org.koin.core.KoinComponent
import org.koin.core.context.stopKoin
import org.koin.core.get
import java.time.Instant

class MessageFilterTest : KoinComponent
{
	init
	{
		withEnvironmentVariable("commandExecute_prefix", "@Wietbot ")
			.and("wietbot_id", "11345663")
			.execute {
				DependencyInjection.bindServiceCollection()
				get<Konfig>()
			}
	}
	
	@After
	fun tearDown() {
		stopKoin()
	}
	
	@Test
	fun `assert that any message from sandbox is passed`()
	{
		val filter: MessageFilter = get()
		
		val event = messageEventOf(
			"test",
			"sandbox",
		)
		
		val result = filter.apply(event)
		
		assertThat(result).isTrue
	}
	
	@Test
	fun `assert that messages starting with @Wietbot from stack overflow are passed`()
	{
		val filter: MessageFilter = get()
		
		val event = messageEventOf(
			"@Wietbot test",
			"stack-overflow",
		)
		
		val result = filter.apply(event)
		
		assertThat(result).isTrue
	}
	
	@Test
	fun `assert that messages from wietbot from stack overflow aren't passed`()
	{
		val filter: MessageFilter = get()
		
		val event = messageEventOf(
			"@Wietbot test",
			"stack-overflow",
			"11345663",
		)
		
		val result = filter.apply(event)
		
		assertThat(result).isFalse
	}
	
	@Test
	fun `assert that not just any message from stack overflow is passed`()
	{
		val filter: MessageFilter = get()
		
		val event = messageEventOf(
			"test",
			"stack-overflow",
		)
		
		val result = filter.apply(event)
		
		assertThat(result).isFalse
	}
	
	private fun messageEventOf(content: String, platform: String, chatUserId: String = ""): MessageEvent
	{
		return MessagePostedEventImpl(
			0,
			0,
			"",
			ContentBuilder.content { text(content) },
			ChatUserImpl(
				chatUserId,
				""
			),
			MessageSourceImpl(
				"",
				""
			),
			platform
		)
	}
}
