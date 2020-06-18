package me.wietlol.wietbot.data.commands.core.repository.models

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Commands : IntIdTable("commands_command")
{
	val keyword = varchar("name", 32).uniqueIndex()
	val listenerQueue = varchar("listenerQueue", 256)
}

class Command(id: EntityID<Int>) : IntEntity(id)
{
	companion object : IntEntityClass<Command>(Commands)
	
	var keyword by Commands.keyword
	var listenerQueue by Commands.listenerQueue
}
