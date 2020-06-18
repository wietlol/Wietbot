package me.wietlol.serialization

interface JsonSerializer2
{
	fun serialize(entity: Any?): String
	
	fun <T> deserialize(json: String, type: Class<T>): T
}

inline fun <reified T> JsonSerializer2.deserialize(json: String): T =
	deserialize(json, T::class.java)
