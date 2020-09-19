package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models

import me.wietlol.reactor.api.ExecutableEvent

class EventMap
{
	private val map: MutableMap<Class<*>, Any> = HashMap()
	
	inline fun <reified T> add(event: ExecutableEvent<T>)
	{
		add(T::class.java, event)
	}
	
	fun <T> add(type: Class<*>, event: ExecutableEvent<T>)
	{
		map[type] = event
	}
	
	@Suppress("UNCHECKED_CAST")
	fun <T> get(type: Class<T>): ExecutableEvent<T>? =
		map[type] as? ExecutableEvent<T>
	
	inline fun <reified T> get(): ExecutableEvent<T>? =
		get(T::class.java)
	
	inline fun <reified T> computeIfAbsent(noinline supplier: (Class<*>) -> ExecutableEvent<T>): ExecutableEvent<T> =
		computeIfAbsent(T::class.java, supplier)
	
	@Suppress("UNCHECKED_CAST")
	fun <T> computeIfAbsent(type: Class<*>, supplier: (Class<*>) -> ExecutableEvent<T>): ExecutableEvent<T> =
		map.computeIfAbsent(type, supplier) as ExecutableEvent<T>
}
