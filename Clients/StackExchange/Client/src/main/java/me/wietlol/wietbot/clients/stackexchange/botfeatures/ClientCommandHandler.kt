package me.wietlol.wietbot.clients.stackexchange.botfeatures

import me.wietlol.wietbot.clients.stackexchange.models.messages.models.BarrelRollRequest
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.ClientCommandRequest
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.ClientCommandResponse
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.ErrorResponseImpl
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.GetInfoRequest
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.GetInfoResponseImpl
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.JoinRoomRequest
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.JoinRoomResponseImpl
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.LeaveRoomRequest
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.LeaveRoomResponseImpl
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.ReconnectRequest
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.ShutdownRequest
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.ShutdownResponseImpl
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.StartClientRequest
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.StartClientResponseImpl
import me.wietlol.wietbot.libraries.stackexchange.chatclient.chatclient.SeCredentials
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.SeWebSocketClient
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.SeWebSocketClientFactory
import java.lang.management.ManagementFactory
import java.lang.management.OperatingSystemMXBean
import java.lang.management.RuntimeMXBean
import kotlin.streams.asStream

class ClientCommandHandler(
	val webSocketClientFactory: SeWebSocketClientFactory,
	val credentials: SeCredentials,
	val initialRoom: Int,
)
{
	private var client: SeWebSocketClient? = null
	
	fun process(command: ClientCommandRequest): ClientCommandResponse =
		when (command)
		{
			is StartClientRequest -> startClient(command)
			is JoinRoomRequest -> joinRoom(command)
			is LeaveRoomRequest -> leaveRoom(command)
			is ReconnectRequest -> reconnect(command)
			is BarrelRollRequest -> barrelRoll(command)
			is ShutdownRequest -> shutdown(command)
			is GetInfoRequest -> getInfo(command)
			else -> TODO()
		}
	
	private fun startClient(command: StartClientRequest): ClientCommandResponse =
		if (client == null)
		{
			client = webSocketClientFactory.create(credentials)
			StartClientResponseImpl()
		}
		else
		{
			ErrorResponseImpl("Connection is already up.")
		}
	
	private fun joinRoom(command: JoinRoomRequest): ClientCommandResponse
	{
		val client = client
		return if (client == null)
		{
			ErrorResponseImpl("Connection is not yet up.")
		}
		else
		{
			client.joinRoom(command.roomId)
			JoinRoomResponseImpl()
		}
	}
	
	private fun leaveRoom(command: LeaveRoomRequest): ClientCommandResponse
	{
		val client = client
		return if (client == null)
		{
			ErrorResponseImpl("Connection is not yet up.")
		}
		else
		{
			client.leaveRoom(command.roomId)
			LeaveRoomResponseImpl()
		}
	}
	
	private fun reconnect(command: ReconnectRequest): ClientCommandResponse
	{
		val client = client
		return if (client == null)
		{
			ErrorResponseImpl("Connection is not yet up.")
		}
		else
		{
			client.reconnect(initialRoom)
			LeaveRoomResponseImpl()
		}
	}
	
	private fun barrelRoll(command: BarrelRollRequest): ClientCommandResponse
	{
		val client = client
		return if (client == null)
		{
			ErrorResponseImpl("Connection is not yet up.")
		}
		else
		{
			val roomId = command.roomId
			(0..100)
				.asSequence()
				.asStream()
				.parallel()
				.forEach {
					client.leaveRoom(roomId)
					client.joinRoom(roomId)
				}
			client.joinRoom(roomId)
			LeaveRoomResponseImpl()
		}
	}
	
	private fun shutdown(command: ShutdownRequest): ClientCommandResponse
	{
		val client = client
		return if (client == null)
		{
			ErrorResponseImpl("Connection is not yet up.")
		}
		else
		{
			client.close()
			this.client = null
			ShutdownResponseImpl()
		}
	}
	
	private fun getInfo(command: GetInfoRequest): ClientCommandResponse
	{
		// todo include information about how long the web socket connection is up
		
		val runtime = Runtime.getRuntime()
		val freeMemory = runtime.freeMemory()
		val totalMemory = runtime.totalMemory()
		val usedMemory = totalMemory - freeMemory
		val maxMemory = runtime.maxMemory()
		val os: OperatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean()
		val runtime2: RuntimeMXBean = ManagementFactory.getRuntimeMXBean()
		
		return GetInfoResponseImpl(
			os.name,
			os.arch,
			os.version,
			os.availableProcessors,
			usedMemory,
			maxMemory,
			runtime2.uptime,
		)
	}
}
