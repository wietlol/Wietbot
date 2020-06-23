package me.wietlol.wietbot.commands.hangman.core.repository.models

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Words : IntIdTable("hangman_word")
{
	val text = varchar("text", 32).uniqueIndex()
}

class Word(id: EntityID<Int>) : IntEntity(id)
{
	companion object : IntEntityClass<Word>(Words)
	
	var text by Words.text
}
