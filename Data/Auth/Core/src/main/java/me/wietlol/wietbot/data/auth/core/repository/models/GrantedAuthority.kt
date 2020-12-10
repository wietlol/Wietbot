package me.wietlol.wietbot.data.auth.core.repository.models

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object GrantedAuthorities : IntIdTable("auth_granted_authority")
{
	val policy = reference("policy", Policies)
	val permission = reference("permission", Permissions)
	val resource = varchar("resource", 256)
	
	init
	{
		index(true, policy, permission, resource)
	}
}

class GrantedAuthority(id: EntityID<Int>) : IntEntity(id), Authority
{
	companion object : IntEntityClass<GrantedAuthority>(GrantedAuthorities)

	override var policy by Policy referencedOn GrantedAuthorities.policy
	override var permission by Permission referencedOn GrantedAuthorities.permission
	override var resource by GrantedAuthorities.resource
}
