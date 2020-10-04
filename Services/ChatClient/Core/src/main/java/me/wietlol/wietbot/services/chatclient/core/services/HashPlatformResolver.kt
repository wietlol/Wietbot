package me.wietlol.wietbot.services.chatclient.core.services

import me.wietlol.wietbot.services.chatclient.core.interfaces.Platform
import me.wietlol.wietbot.services.chatclient.core.interfaces.PlatformResolver

class HashPlatformResolver(
	val registry: Map<String, () -> Platform>
) : PlatformResolver
{
	override fun getByName(name: String): Platform =
		registry[name]
			?.invoke()
			?: throw Exception("Invalid platform name '${name}'.")
}
