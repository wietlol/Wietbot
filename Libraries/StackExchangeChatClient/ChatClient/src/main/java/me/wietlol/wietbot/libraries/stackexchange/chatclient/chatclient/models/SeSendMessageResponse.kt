package me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class SeSendMessageResponse(
	var id: Int = 0
)
