package me.wietlol.wietbot.data.auth.core.repository.models

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object RevokedAuthorities : IntIdTable("auth_revoked_authority")
{
	val policy = reference("policy", Policies)
	val permission = reference("permission", Permissions)
	val resource = varchar("resource", 256)
	
	init
	{
		GrantedAuthorities.index(true, GrantedAuthorities.policy, GrantedAuthorities.permission)
	}
}

class RevokedAuthority(id: EntityID<Int>) : IntEntity(id), Authority
{
	companion object : IntEntityClass<RevokedAuthority>(RevokedAuthorities)

	override var policy by Policy referencedOn RevokedAuthorities.policy
	override var permission by Permission referencedOn RevokedAuthorities.permission
	override var resource by RevokedAuthorities.resource
}
