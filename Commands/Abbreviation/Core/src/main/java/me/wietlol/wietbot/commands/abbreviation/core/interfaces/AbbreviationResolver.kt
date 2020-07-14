package me.wietlol.wietbot.commands.abbreviation.core.interfaces

import me.wietlol.wietbot.commands.abbreviation.core.models.AbbreviationSearchResult
import me.wietlol.wietbot.commands.abbreviation.core.models.Definition

interface AbbreviationResolver
{
	fun findDefinitions(abbreviation: String): AbbreviationSearchResult
}
