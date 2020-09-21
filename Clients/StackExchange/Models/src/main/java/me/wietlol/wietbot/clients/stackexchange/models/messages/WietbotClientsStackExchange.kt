// hash: #bac0507c
// @formatter:off
package me.wietlol.wietbot.clients.stackexchange.models.messages

import me.wietlol.bitblock.api.registry.ModelRegistry
import me.wietlol.bitblock.api.registry.ModelRegistryKey
import me.wietlol.bitblock.api.registry.RegistrySet
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.core.BitBlockBase
import me.wietlol.bitblock.core.registry.CommonModelRegistryKey
import me.wietlol.wietbot.clients.stackexchange.models.messages.serializers.*

object WietbotClientsStackExchange : RegistrySet
{
	val modelSerializers: Map<ModelRegistryKey, ModelSerializer<*, *>>
		= createModelSerializers()
	
	private fun createModelSerializers(): Map<ModelRegistryKey, ModelSerializer<*, *>> =
		mapOf(
			CommonModelRegistryKey("ReverseTextRequest", "WietbotClientsStackExchange", "Wietbot", "1.0") to ReverseTextRequestSerializer,
			CommonModelRegistryKey("ReverseTextResponse", "WietbotClientsStackExchange", "Wietbot", "1.0") to ReverseTextResponseSerializer,
			CommonModelRegistryKey("StartClientRequest", "WietbotClientsStackExchange", "Wietbot", "1.0") to StartClientRequestSerializer,
			CommonModelRegistryKey("StartClientResponse", "WietbotClientsStackExchange", "Wietbot", "1.0") to StartClientResponseSerializer,
			CommonModelRegistryKey("ListCommandsRequest", "WietbotClientsStackExchange", "Wietbot", "1.0") to ListCommandsRequestSerializer,
			CommonModelRegistryKey("ListCommandsResponse", "WietbotClientsStackExchange", "Wietbot", "1.0") to ListCommandsResponseSerializer,
			CommonModelRegistryKey("JoinRoomRequest", "WietbotClientsStackExchange", "Wietbot", "1.0") to JoinRoomRequestSerializer,
			CommonModelRegistryKey("JoinRoomResponse", "WietbotClientsStackExchange", "Wietbot", "1.0") to JoinRoomResponseSerializer,
			CommonModelRegistryKey("LeaveRoomRequest", "WietbotClientsStackExchange", "Wietbot", "1.0") to LeaveRoomRequestSerializer,
			CommonModelRegistryKey("LeaveRoomResponse", "WietbotClientsStackExchange", "Wietbot", "1.0") to LeaveRoomResponseSerializer,
			CommonModelRegistryKey("ReconnectRequest", "WietbotClientsStackExchange", "Wietbot", "1.0") to ReconnectRequestSerializer,
			CommonModelRegistryKey("ReconnectResponse", "WietbotClientsStackExchange", "Wietbot", "1.0") to ReconnectResponseSerializer,
			CommonModelRegistryKey("BarrelRollRequest", "WietbotClientsStackExchange", "Wietbot", "1.0") to BarrelRollRequestSerializer,
			CommonModelRegistryKey("BarrelRollResponse", "WietbotClientsStackExchange", "Wietbot", "1.0") to BarrelRollResponseSerializer,
			CommonModelRegistryKey("ShutdownRequest", "WietbotClientsStackExchange", "Wietbot", "1.0") to ShutdownRequestSerializer,
			CommonModelRegistryKey("ShutdownResponse", "WietbotClientsStackExchange", "Wietbot", "1.0") to ShutdownResponseSerializer,
			CommonModelRegistryKey("GetInfoRequest", "WietbotClientsStackExchange", "Wietbot", "1.0") to GetInfoRequestSerializer,
			CommonModelRegistryKey("GetInfoResponse", "WietbotClientsStackExchange", "Wietbot", "1.0") to GetInfoResponseSerializer,
			CommonModelRegistryKey("ErrorResponse", "WietbotClientsStackExchange", "Wietbot", "1.0") to ErrorResponseSerializer,
			CommonModelRegistryKey("ChatEventType", "WietbotClientsStackExchange", "Wietbot", "1.0") to ChatEventTypeSerializer,
			CommonModelRegistryKey("MessageEventList", "WietbotClientsStackExchange", "Wietbot", "1.0") to MessageEventListSerializer,
			CommonModelRegistryKey("MessagePostedEvent", "WietbotClientsStackExchange", "Wietbot", "1.0") to MessagePostedEventSerializer,
			CommonModelRegistryKey("MessageEditedEvent", "WietbotClientsStackExchange", "Wietbot", "1.0") to MessageEditedEventSerializer,
			CommonModelRegistryKey("MessageDeletedEvent", "WietbotClientsStackExchange", "Wietbot", "1.0") to MessageDeletedEventSerializer,
			CommonModelRegistryKey("User", "WietbotClientsStackExchange", "Wietbot", "1.0") to UserSerializer,
			CommonModelRegistryKey("Room", "WietbotClientsStackExchange", "Wietbot", "1.0") to RoomSerializer,
		)
	
	override fun initialize(registry: ModelRegistry?) =
		modelSerializers.forEach((registry ?: BitBlockBase.modelRegistry)::set)
}
// @formatter:on
