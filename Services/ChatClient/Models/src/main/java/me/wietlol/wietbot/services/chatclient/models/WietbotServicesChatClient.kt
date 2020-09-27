// hash: #635e7cb0
// @formatter:off
package me.wietlol.wietbot.services.chatclient.models

import me.wietlol.bitblock.api.registry.ModelRegistry
import me.wietlol.bitblock.api.registry.ModelRegistryKey
import me.wietlol.bitblock.api.registry.RegistrySet
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.core.BitBlockBase
import me.wietlol.bitblock.core.registry.CommonModelRegistryKey
import me.wietlol.wietbot.services.chatclient.models.serializers.*

object WietbotServicesChatClient : RegistrySet
{
	val modelSerializers: Map<ModelRegistryKey, ModelSerializer<*, *>>
		= createModelSerializers()
	
	private fun createModelSerializers(): Map<ModelRegistryKey, ModelSerializer<*, *>> =
		mapOf(
			CommonModelRegistryKey("SendMessageRequest", "WietbotServicesChatClient", "Wietbot", "1.0") to SendMessageRequestSerializer,
			CommonModelRegistryKey("SendMessageResponse", "WietbotServicesChatClient", "Wietbot", "1.0") to SendMessageResponseSerializer,
			CommonModelRegistryKey("EditMessageRequest", "WietbotServicesChatClient", "Wietbot", "1.0") to EditMessageRequestSerializer,
			CommonModelRegistryKey("EditMessageResponse", "WietbotServicesChatClient", "Wietbot", "1.0") to EditMessageResponseSerializer,
		)
	
	override fun initialize(registry: ModelRegistry?) =
		modelSerializers.forEach((registry ?: BitBlockBase.modelRegistry)::set)
}
// @formatter:on
