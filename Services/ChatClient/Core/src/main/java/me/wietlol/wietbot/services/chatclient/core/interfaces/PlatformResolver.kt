package me.wietlol.wietbot.services.chatclient.core.interfaces

interface PlatformResolver
{
	fun getByName(name: String): Platform
}
