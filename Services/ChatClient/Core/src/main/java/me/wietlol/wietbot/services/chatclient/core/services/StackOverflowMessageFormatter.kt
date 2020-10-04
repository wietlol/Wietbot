package me.wietlol.wietbot.services.chatclient.core.services

import me.wietlol.wietbot.data.messages.models.models.BoldPart
import me.wietlol.wietbot.data.messages.models.models.Content
import me.wietlol.wietbot.data.messages.models.models.ContentPart
import me.wietlol.wietbot.data.messages.models.models.InlineMonospacedPart
import me.wietlol.wietbot.data.messages.models.models.ItalicPart
import me.wietlol.wietbot.data.messages.models.models.MonospacedPart
import me.wietlol.wietbot.data.messages.models.models.MultiPart
import me.wietlol.wietbot.data.messages.models.models.QuotePart
import me.wietlol.wietbot.data.messages.models.models.StrikeoutPart
import me.wietlol.wietbot.data.messages.models.models.TagPart
import me.wietlol.wietbot.data.messages.models.models.TextPart
import me.wietlol.wietbot.data.messages.models.models.UrlPart

class StackOverflowMessageFormatter
{
	fun format(content: Content): String =
		format(content.part)
	
	fun format(part: ContentPart): String =
		part.format()
	
	@JvmName("formatContentPart")
	private fun ContentPart.format(): String =
		when (this)
		{
			is MultiPart -> parts.joinToString("") { it.format() }
			is TextPart -> text
			is MonospacedPart -> text.lines().joinToString("\n") { "    $it" }
			is InlineMonospacedPart -> "`$text`"
			is UrlPart -> "[${text.format()}]($href${title?.let { " ${it.format()}" } ?: ""})"
			is QuotePart -> "> ${inner.format()}"
			is BoldPart -> "**${inner.format()}**"
			is ItalicPart -> "_${inner.format()}_"
			is StrikeoutPart -> "---${inner.format()}---"
			is TagPart -> "[tag:$name]"
			else -> ""
		}
}
