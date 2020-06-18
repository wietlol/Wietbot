package me.wietlol.wietbot.data.commands.core.repository

import me.wietlol.konfig.api.KonfigName

data class DatabaseSettings(
	@KonfigName("host") val host: String,
	@KonfigName("name") val name: String,
	@KonfigName("driver") val driver: String,
	@KonfigName("user") val user: String,
	@KonfigName("password") val password: String
)
{
	val url: String
		get() = "jdbc:mysql://$host:3306/$name?serverTimezone=UTC"
}
