package me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient

import me.wietlol.konfig.api.KonfigName

data class SeCredentials(
	@KonfigName("emailAddress") val emailAddress: String,
	@KonfigName("password") val password: String
)
