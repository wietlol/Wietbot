package me.wietlol.wietbot.commands.abbreviation.core.interfaces

import me.wietlol.wietbot.commands.abbreviation.core.models.AbbreviationSearchResult

interface AbbreviationResolver
{
	fun findDefinitions(abbreviation: String): AbbreviationSearchResult
}
