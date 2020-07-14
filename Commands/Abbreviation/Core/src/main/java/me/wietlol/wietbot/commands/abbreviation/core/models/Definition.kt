package me.wietlol.wietbot.commands.abbreviation.core.models

data class Definition(
	val abbreviation: String,
	val description: String,
	val rating: Int,
	val tags: List<String>
)
