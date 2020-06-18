package me.wietlol.wietbot.commands.evalsql.core.services

import me.wietlol.konfig.api.KonfigName

data class DatabaseSettings(
	@KonfigName("driver") val driver: String,
	@KonfigName("url") val url: String,
	@KonfigName("user") val user: String,
	@KonfigName("password") val password: String
)
