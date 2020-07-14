package me.wietlol.wietbot.commands.abbreviation.core.models

data class AbbreviationSearchResult(
	val definitions: List<Definition>,
	val suggestions: List<String>
)
