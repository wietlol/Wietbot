package me.wietlol.wietbot.data.commands.models

import me.wietlol.bitblock.api.registry.ModelRegistry
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.core.BitBlock
import me.wietlol.bitblock.core.registry.CommonModelRegistryKey
import me.wietlol.wietbot.data.commands.models.serializers.*

object WietbotDataCommands
{
	val modelSerializers: Map<CommonModelRegistryKey, ModelSerializer<*, *>> = mapOf(
		 CommonModelRegistryKey("ListCommandsRequest", "WietbotDataCommands", "Wietbot", "1.0") to ListCommandsRequestSerializer,
		 CommonModelRegistryKey("ListCommandsResponse", "WietbotDataCommands", "Wietbot", "1.0") to ListCommandsResponseSerializer,
		 CommonModelRegistryKey("CreateCommandRequest", "WietbotDataCommands", "Wietbot", "1.0") to CreateCommandRequestSerializer,
		 CommonModelRegistryKey("CreateCommandResponse", "WietbotDataCommands", "Wietbot", "1.0") to CreateCommandResponseSerializer,
		 CommonModelRegistryKey("RemoveCommandRequest", "WietbotDataCommands", "Wietbot", "1.0") to RemoveCommandRequestSerializer,
		 CommonModelRegistryKey("RemoveCommandResponse", "WietbotDataCommands", "Wietbot", "1.0") to RemoveCommandResponseSerializer,
		 CommonModelRegistryKey("Command", "WietbotDataCommands", "Wietbot", "1.0") to CommandSerializer,
		 CommonModelRegistryKey("CommandCall", "WietbotDataCommands", "Wietbot", "1.0") to CommandCallSerializer,
		 CommonModelRegistryKey("Message", "WietbotDataCommands", "Wietbot", "1.0") to MessageSerializer,
		 CommonModelRegistryKey("User", "WietbotDataCommands", "Wietbot", "1.0") to UserSerializer,
		 CommonModelRegistryKey("Room", "WietbotDataCommands", "Wietbot", "1.0") to RoomSerializer
	)
	
	fun initialize() = initialize(BitBlock.modelRegistry)
	
	fun initialize(registry: ModelRegistry) =
		modelSerializers.forEach { (key, value) ->
			registry[key] = value
		}
}
