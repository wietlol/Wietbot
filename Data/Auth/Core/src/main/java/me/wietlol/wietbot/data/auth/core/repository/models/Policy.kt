package me.wietlol.wietbot.data.auth.core.repository.models

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Policies : IntIdTable("auth_policy")
{
	val name = varchar("name", 256).uniqueIndex()
}

class Policy(id: EntityID<Int>) : IntEntity(id)
{
	companion object : IntEntityClass<Policy>(Policies)

	var name by Policies.name

	val grants by GrantedAuthority referrersOn GrantedAuthorities.policy
	val revokes by RevokedAuthority referrersOn RevokedAuthorities.policy

	var roles by Role via RolePolicies
}
