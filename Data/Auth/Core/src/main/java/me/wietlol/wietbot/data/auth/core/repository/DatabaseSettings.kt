package me.wietlol.wietbot.data.auth.core.repository

data class DatabaseSettings(
	val host: String,
	val name: String,
	val driver: String,
	val user: String,
	val password: String
)
{
	val url: String
		get() = "jdbc:mysql://$host:3306/$name?serverTimezone=UTC"
}
