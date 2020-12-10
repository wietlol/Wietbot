package me.wietlol.wietbot.data.auth.core.interfaces

import me.wietlol.wietbot.data.auth.core.repository.models.GrantedAuthority
import me.wietlol.wietbot.data.auth.core.repository.models.Permission
import me.wietlol.wietbot.data.auth.core.repository.models.Policy
import me.wietlol.wietbot.data.auth.core.repository.models.RevokedAuthority
import me.wietlol.wietbot.data.auth.core.repository.models.Role
import me.wietlol.wietbot.data.auth.core.repository.models.User

interface AuthRepository
{
	fun getOrCreateUser(id: String, platform: String, name: String): User
	
	fun createPermission(name: String): Permission
	
	fun createRole(name: String): Role
	
	fun createPolicy(name: String): Policy
	
	fun createGrantedAuthority(policy: String, permission: String, resource: String): GrantedAuthority
	
	fun createRevokedAuthority(policy: String, permission: String, resource: String): RevokedAuthority
	
	fun attachRolePolicy(role: String, policy: String)
	
	fun detachRolePolicy(role: String, policy: String)
	
	fun getUserRole(userId: String, platform: String): Role
	
	fun setUserRole(userId: String, platform: String, roleName: String)
	
	fun listRoles(): List<Role>
	
	fun isUserAuthorized(userId: String, platform: String, permission: String, resource: String): Boolean
}
