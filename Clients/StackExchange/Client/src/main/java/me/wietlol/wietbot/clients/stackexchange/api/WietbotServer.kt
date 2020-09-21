package me.wietlol.wietbot.clients.stackexchange.api

import me.wietlol.wietbot.clients.stackexchange.models.messages.models.ClientCommandRequest
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.ClientCommandResponse

class WietbotServer(
	val bitConnect: BitConnect<ClientCommandRequest, ClientCommandResponse>,
	val port: Int,
)
{
	fun start(): Nothing
	{
		bitConnect.start(port)
	}
}
