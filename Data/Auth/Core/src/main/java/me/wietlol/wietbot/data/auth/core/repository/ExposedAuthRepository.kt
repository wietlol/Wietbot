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
import me.wietlol.wietbot.data.auth.models.models.PlatformImpl
import org.jetbrains.exposed.sql.Column
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
	
	override fun getOrCreateUser(id: String, platform: String, name: String): User =
		transaction {
			User.find { getPrimaryKey(platform) eq id }
				.firstOrNull()
				?.also {
					it.setLocalName(platform, name) // todo, not sure if this is enough or if we have to save it somehow
				}
				?: createUser(id, platform, name)
		}
	
	private fun createUser(id: String, platform: String, name: String): User =
		transaction {
			User.new {
				setPrimaryKey(platform, id)
				setLocalName(platform, name)
				role = Role.find { Roles.name eq "pleb" }.single()
			}
		}
	
	override fun isUserAuthorized(userId: String, platform: String, permission: String, resource: String): Boolean =
		transaction {
			User.find { getPrimaryKey(platform) eq userId }
				.singleOrNull()
				?.let { user ->
					val policies = user.role.policies
					
					val isGranted = policies.flatMap { it.grants }.any { it.appliesOn(permission, resource) }
					val isRevoked = policies.flatMap { it.revokes }.any { it.appliesOn(permission, resource) }
					
					isGranted && isRevoked.not()
				}
				?: run {
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
	
	override fun getUserRole(userId: String, platform: String): Role =
		transaction {
			User.find { getPrimaryKey(platform) eq userId }
				.single()
				.role
		}
	
	override fun setUserRole(userId: String, platform: String, roleName: String) =
		transaction {
			User.find { getPrimaryKey(platform) eq userId }
				.single()
				.role = Role.find { Roles.name eq roleName }.single()
		}
	
	override fun listRoles(): List<Role> =
		transaction {
			Role.all().toList()
		}
	
	private fun User.setPrimaryKey(platform: String, id: String): Unit =
		when (platform)
		{
			PlatformImpl.stackOverflow.name -> stackOverflowName = id
			PlatformImpl.discord.name -> discordId = id
			PlatformImpl.wietbotWebsite.name -> wietbotWebsiteId = id
			else -> TODO("Not yet implemented.")
		}
	
	private fun getPrimaryKey(platform: String): Column<String> =
		when (platform)
		{
			PlatformImpl.stackOverflow.name -> Users.stackOverflowName
			PlatformImpl.discord.name -> Users.discordId
			PlatformImpl.wietbotWebsite.name -> Users.wietbotWebsiteId
			else -> TODO("Not yet implemented.")
		}
	
	private fun User.setLocalName(platform: String, name: String)
	{
		when (platform)
		{
			PlatformImpl.stackOverflow.name -> stackOverflowName = name
			PlatformImpl.discord.name -> discordName = name
			PlatformImpl.wietbotWebsite.name -> wietbotWebsiteName = name
			else -> TODO("Not yet implemented.")
		}
	}
}
