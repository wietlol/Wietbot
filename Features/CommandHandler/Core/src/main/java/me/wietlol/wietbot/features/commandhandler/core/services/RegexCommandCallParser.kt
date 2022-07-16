package me.wietlol.wietbot.features.commandhandler.core.services

import me.wietlol.wietbot.data.commands.models.models.CommandCall
import me.wietlol.wietbot.data.commands.models.models.CommandCallImpl
import me.wietlol.wietbot.data.messages.models.models.MessageEvent
import me.wietlol.wietbot.data.messages.models.models.MessageEventList
import me.wietlol.wietbot.data.messages.models.simpleText
import me.wietlol.wietbot.features.commandhandler.core.interfaces.CommandCallParser

class RegexCommandCallParser(
	prefixMap: Map<String, String>,
) : CommandCallParser
{
	private val prefixRegexMap: Map<String, Pair<String, Regex>> = prefixMap
		.mapValues { it.value to Regex("^(?i)${it.value}\\s+") }
	private val optionRegex = Regex("^-(?<key>[a-zA-Z0-9._-]+)(?:=(?:(?<value1>[^\"][^ ]*)|(?:\"(?<value2>(?:\"\"|[^\"])*)\")))?")
	private val commandRegex = Regex("^([a-zA-Z0-9_-]+) ?(?s)(.*)\$")
	
	override fun parseCommand(event: MessageEventList): CommandCall?
	{
		val last: MessageEvent = event.events.last() as MessageEvent
		val text: String = last.content.simpleText.trim()
		
		val prefix = prefixRegexMap[last.platform]
		if (prefix != null && prefix.second.find(text) == null)
			return null
		
		val options = mutableMapOf<String, String>()
		var tempText =
			if (prefix != null)
				text.drop(prefix.first.length).trim()
			else
				text.trim()
		while (tempText.startsWith("-"))
		{
			val match = optionRegex.find(tempText)
				?: break
			
			val key = match.groups["key"]!!.value
			val value = match.groups["value1"]?.value
				?: match.groups["value2"]?.value
				?: ""
			
			options[key] = value
			
			tempText = tempText.drop(match.value.length).trim()
		}
		
		val match = commandRegex.matchEntire(tempText)
			?: return null
		
		return CommandCallImpl(
			match.groupValues[1],
			match.groupValues[2],
			event,
			options
		)
	}
}
