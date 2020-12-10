package me.wietlol.wietbot.data.auth.core.repository.models

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.LongIdTable

object Users : LongIdTable("auth_user")
{
	val stackOverflowId = varchar("stackOverflowId", 256).index()
	val stackOverflowName = varchar("stackOverflowName", 512).index()
	val discordId = varchar("discordId", 256).index()
	val discordName = varchar("discordName", 512).index()
	val wietbotWebsiteId = varchar("wietbotWebsiteId", 256).index()
	val wietbotWebsiteName = varchar("wietbotWebsiteName", 512).index()
	val role = reference("role", Roles)
}

class User(id: EntityID<Long>) : LongEntity(id)
{
	companion object : LongEntityClass<User>(Users)
	
	var stackOverflowId by Users.stackOverflowId
	var stackOverflowName by Users.stackOverflowName
	var discordId by Users.discordId
	var discordName by Users.discordName
	var wietbotWebsiteId by Users.wietbotWebsiteId
	var wietbotWebsiteName by Users.wietbotWebsiteName
	var role by Role referencedOn Users.role
}
