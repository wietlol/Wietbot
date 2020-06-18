package me.wietlol.wietbot.data.auth.core.repository

import me.wietlol.wietbot.data.auth.core.interfaces.AuthRepository
import me.wietlol.wietbot.data.auth.core.repository.models.Authority
import me.wietlol.wietbot.data.auth.core.repository.models.GrantedAuthority
import me.wietlol.wietbot.data.auth.core.repository.models.Permission
import me.wietlol.wietbot.data.auth.core.repository.models.Permissions
import me.wietlol.wietbot.data.auth.core.repository.models.Policies
import me.wietlol.wietbot.data.auth.core.repository.models.Policy
import me.wietlol.wietbot.data.auth.core.repository.models.RevokedAuthority
import me.wietlol.wietbot.data.auth.core.repository.models.Role
import me.wietlol.wietbot.data.auth.core.repository.models.Roles
import me.wietlol.wietbot.data.auth.core.repository.models.User
import me.wietlol.wietbot.data.auth.core.repository.models.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction

class ExposedAuthRepository(
	@Suppress("unused") val database: Database // make sure a database connection is set up
) : AuthRepository
{
	override fun createPermission(name: String): Permission =
		transaction {
			Permission.new {
				this.name = name
			}
		}
	
	override fun getOrCreateUser(id: Int, name: String): User =
		transaction {
			User.find { Users.stackExchangeId eq id }
				.firstOrNull()
				?.also { it.name = name }
				?: createUser(id, name)
		}
	
	private fun createUser(id: Int, name: String): User =
		transaction {
			User.new {
				stackExchangeId = id
				this.name = name
				role = Role.find { Roles.name eq "pleb" }.single()
			}
		}
	
	override fun isUserAuthorized(userId: Int, permission: String, resource: String): Boolean =
		transaction {
			println("checking if user ($userId) is authorized to perform ($permission) on resource ($resource)")
			User.find { Users.stackExchangeId eq userId }
				.singleOrNull()
				?.let { user ->
					val policies = user.role.policies
					
					val isGranted = policies.flatMap { it.grants }.any { it.appliesOn(permission, resource) }
					val isRevoked = policies.flatMap { it.revokes }.any { it.appliesOn(permission, resource) }
					
					println("permission is ${if (isGranted) "" else "not "}granted")
					println("permission is ${if (isRevoked) "" else "not "}revoked")
					
					isGranted && isRevoked.not()
				}
				?: run {
					println("user not found, no permission granted")
					false
				}
		}
	
	private fun Authority.appliesOn(permission: String, resource: String): Boolean =
		this.permission.name == permission && (this.resource == "*" || this.resource == resource)
	
	override fun createRole(name: String): Role =
		transaction {
			Role.new {
				this.name = name
			}
		}
	
	override fun createPolicy(name: String): Policy =
		transaction {
			Policy.new {
				this.name = name
			}
		}
	
	override fun createGrantedAuthority(policy: String, permission: String, resource: String): GrantedAuthority =
		transaction {
			GrantedAuthority.new {
				this.policy = Policy.find { Policies.name eq policy }.single()
				this.permission = Permission.find { Permissions.name eq permission }.single()
				this.resource = resource
			}
		}
	
	override fun createRevokedAuthority(policy: String, permission: String, resource: String): RevokedAuthority =
		transaction {
			RevokedAuthority.new {
				this.policy = Policy.find { Policies.name eq policy }.single()
				this.permission = Permission.find { Permissions.name eq permission }.single()
				this.resource = resource
			}
		}
	
	override fun attachRolePolicy(role: String, policy: String) =
		transaction {
			Role.find { Roles.name eq role }
				.single()
				.let {
					it.policies = SizedCollection(it.policies + Policy.find { Policies.name eq policy }.single())
				}
		}
	
	override fun detachRolePolicy(role: String, policy: String) =
		transaction {
			Role.find { Roles.name eq role }
				.single()
				.run {
					policies = SizedCollection(policies.filter { it.name != policy })
				}
		}
	
	override fun getUserRole(userId: Int): Role =
		transaction {
			User.find { Users.stackExchangeId eq userId }
				.single()
				.role
		}
	
	override fun setUserRole(userId: Int, roleName: String) =
		transaction {
			User.find { Users.stackExchangeId eq userId }
				.single()
				.role = Role.find { Roles.name eq roleName }.single()
		}
	
	override fun listRoles(): List<Role> =
		transaction {
			Role.all().toList()
		}
}
