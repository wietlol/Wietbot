package me.wietlol.wietbot.services.chatclient.models

import me.wietlol.bitblock.api.registry.ModelRegistry
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.core.BitBlock
import me.wietlol.bitblock.core.registry.CommonModelRegistryKey
import me.wietlol.wietbot.services.chatclient.models.serializers.*

object WietbotServicesChatClient
{
	val modelSerializers: Map<CommonModelRegistryKey, ModelSerializer<*, *>> = mapOf(
		 CommonModelRegistryKey("SendMessageRequest", "WietbotServicesChatClient", "Wietbot", "1.0") to SendMessageRequestSerializer,
		 CommonModelRegistryKey("SendMessageRetryRequest", "WietbotServicesChatClient", "Wietbot", "1.0") to SendMessageRetryRequestSerializer,
		 CommonModelRegistryKey("SendMessageResponse", "WietbotServicesChatClient", "Wietbot", "1.0") to SendMessageResponseSerializer,
		 CommonModelRegistryKey("EditMessageRequest", "WietbotServicesChatClient", "Wietbot", "1.0") to EditMessageRequestSerializer,
		 CommonModelRegistryKey("EditMessageRetryRequest", "WietbotServicesChatClient", "Wietbot", "1.0") to EditMessageRetryRequestSerializer,
		 CommonModelRegistryKey("EditMessageResponse", "WietbotServicesChatClient", "Wietbot", "1.0") to EditMessageResponseSerializer
	)
	
	fun initialize() = initialize(BitBlock.modelRegistry)
	
	fun initialize(registry: ModelRegistry) =
		modelSerializers.forEach { (key, value) ->
			registry[key] = value
		}
}
