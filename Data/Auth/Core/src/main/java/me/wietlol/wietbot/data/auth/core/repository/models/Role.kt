package me.wietlol.wietbot.data.auth.core.repository.models

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Roles : IntIdTable("auth_role")
{
	val name = varchar("name", 256).uniqueIndex()
}

class Role(id: EntityID<Int>) : IntEntity(id)
{
	companion object : IntEntityClass<Role>(Roles)

	var name by Roles.name

	var policies by Policy via RolePolicies
}
