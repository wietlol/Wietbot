package me.wietlol.wietbot.data.auth.core.repository.models

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Users : IntIdTable("auth_user")
{
	val stackExchangeId = integer("stackExchangeId").uniqueIndex()
	val name = varchar("name", 512).index()
	val role = reference("role", Roles)
}

class User(id: EntityID<Int>) : IntEntity(id)
{
	companion object : IntEntityClass<User>(Users)
	
	var stackExchangeId by Users.stackExchangeId
	var name by Users.name
	var role by Role referencedOn Users.role
}
