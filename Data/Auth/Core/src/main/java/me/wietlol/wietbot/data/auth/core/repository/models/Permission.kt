package me.wietlol.wietbot.data.auth.core.repository.models

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Permissions : IntIdTable("auth_permission")
{
	val name = varchar("name", 256).uniqueIndex()
}

class Permission(id: EntityID<Int>) : IntEntity(id)
{
	companion object : IntEntityClass<Permission>(Permissions)

	var name by Permissions.name
}
