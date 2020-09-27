// hash: #3d62f280
// @formatter:off
package me.wietlol.wietbot.data.commands.models

import me.wietlol.bitblock.api.registry.ModelRegistry
import me.wietlol.bitblock.api.registry.ModelRegistryKey
import me.wietlol.bitblock.api.registry.RegistrySet
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.core.BitBlockBase
import me.wietlol.bitblock.core.registry.CommonModelRegistryKey
import me.wietlol.wietbot.data.commands.models.serializers.*

object WietbotDataCommands : RegistrySet
{
	val modelSerializers: Map<ModelRegistryKey, ModelSerializer<*, *>>
		= createModelSerializers()
	
	private fun createModelSerializers(): Map<ModelRegistryKey, ModelSerializer<*, *>> =
		mapOf(
			CommonModelRegistryKey("ListCommandsRequest", "WietbotDataCommands", "Wietbot", "1.0") to ListCommandsRequestSerializer,
			CommonModelRegistryKey("ListCommandsResponse", "WietbotDataCommands", "Wietbot", "1.0") to ListCommandsResponseSerializer,
			CommonModelRegistryKey("CreateCommandRequest", "WietbotDataCommands", "Wietbot", "1.0") to CreateCommandRequestSerializer,
			CommonModelRegistryKey("CreateCommandResponse", "WietbotDataCommands", "Wietbot", "1.0") to CreateCommandResponseSerializer,
			CommonModelRegistryKey("RemoveCommandRequest", "WietbotDataCommands", "Wietbot", "1.0") to RemoveCommandRequestSerializer,
			CommonModelRegistryKey("RemoveCommandResponse", "WietbotDataCommands", "Wietbot", "1.0") to RemoveCommandResponseSerializer,
			CommonModelRegistryKey("Command", "WietbotDataCommands", "Wietbot", "1.0") to CommandSerializer,
			CommonModelRegistryKey("CommandCall", "WietbotDataCommands", "Wietbot", "1.0") to CommandCallSerializer,
			CommonModelRegistryKey("Message", "WietbotDataCommands", "Wietbot", "1.0") to MessageSerializer,
			CommonModelRegistryKey("ChatUser", "WietbotDataCommands", "Wietbot", "1.0") to ChatUserSerializer,
			CommonModelRegistryKey("MessageSource", "WietbotDataCommands", "Wietbot", "1.0") to MessageSourceSerializer,
		)
	
	override fun initialize(registry: ModelRegistry?) =
		modelSerializers.forEach((registry ?: BitBlockBase.modelRegistry)::set)
}
// @formatter:on
