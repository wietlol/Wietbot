package me.wietlol.wietbot.clients.stackexchange.api

import me.wietlol.bitblock.api.serialization.deserialize
import me.wietlol.bitblock.core.BitBlockBase
import me.wietlol.bitblock.core.BitSchemaBuilder
import me.wietlol.wietbot.clients.stackexchange.models.messages.WietbotClientsStackExchange
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.ClientCommandResponse
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.ErrorResponse
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.GetInfoRequestImpl
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.GetInfoResponse
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.ShutdownRequestImpl
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.ShutdownResponse
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.StartClientRequestImpl
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.StartClientResponse
import java.net.Socket

object Client
{
	@JvmStatic
	fun main(args: Array<String>)
	{
		val host = "ec2-54-195-182-159.eu-west-1.compute.amazonaws.com"
		val port = 25950
		
		val schema = BitSchemaBuilder.buildSchema(
			WietbotClientsStackExchange::class.java.getResourceAsStream("/me/wietlol/wietbot/clients/stackexchange/models/SeChatMessages.bitschema"),
			listOf(BitBlockBase, WietbotClientsStackExchange),
		)
		
		Socket(host, port).use { socket ->
			val input = socket.getInputStream()
			val output = socket.getOutputStream()
			
			val request = when (1)
			{
				1 -> StartClientRequestImpl()
				2 -> ShutdownRequestImpl()
				else -> GetInfoRequestImpl()
			}
			
			schema.serialize(output, request)
			when (val response: ClientCommandResponse? = schema.deserialize(input))
			{
				is GetInfoResponse ->
				{
					println(response.name)
					println(response.version)
					println(response.architecture)
					println(response.usedMemory)
					println(response.maxMemory)
					println(response.runtimeSince)
					println(response.clientIsRunning)
					println(response.clientSince)
				}
				is StartClientResponse ->
				{
					println("web socket connection started")
				}
				is ShutdownResponse ->
				{
					println("web socket connection stopped")
				}
				is ErrorResponse ->
				{
					println("error:")
					println(response.message)
				}
				else ->
				{
					println("unknown")
				}
			}
		}
	}
}
