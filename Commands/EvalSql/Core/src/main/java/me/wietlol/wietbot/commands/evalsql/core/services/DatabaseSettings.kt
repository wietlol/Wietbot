package me.wietlol.wietbot.commands.evalsql.core.services

data class DatabaseSettings(
	val driver: String,
	val url: String,
	val user: String,
	val password: String
)
