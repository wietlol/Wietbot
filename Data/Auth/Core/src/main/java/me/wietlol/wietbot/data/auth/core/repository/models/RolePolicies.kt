package me.wietlol.wietbot.data.auth.core.repository.models

import org.jetbrains.exposed.sql.Table

object RolePolicies : Table("auth_role_policy")
{
	val policy = reference("policy", Policies).primaryKey(0)
	val role = reference("role", Roles).primaryKey(1)
}
