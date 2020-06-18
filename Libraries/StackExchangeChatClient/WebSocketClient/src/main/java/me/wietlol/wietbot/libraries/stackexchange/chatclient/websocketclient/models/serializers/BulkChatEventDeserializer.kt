package me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.serializers

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.BulkChatEvent
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.EventType
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.MessageDeleted
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.MessageEdited
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.MessageMovedIn
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.MessageMovedOut
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.MessagePosted
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.MessageStarred
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.ReplyPosted
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.SeChatEvent
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.UserJoined
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.UserLeft
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.models.UserMentioned

class BulkChatEventDeserializer(
	val objectMapper: ObjectMapper
) : StdDeserializer<BulkChatEvent>(null as Class<*>?)
{
	override fun deserialize(parser: JsonParser, context: DeserializationContext): BulkChatEvent
	{
		// SE-chat uses snake_case for property naming, so we use the snake case while deserializing the events
		val oldNaming = objectMapper.propertyNamingStrategy
		try
		{
			objectMapper.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
			
			val node: JsonNode = parser.codec.readTree(parser)
			val events = node
				.flatMap { it["e"] ?: emptyList<JsonNode>() }
				.map { parseEvent(it) }
				.distinctBy { it.id }
				.map { it.htmlDecode() }
				.toList()
			
			return BulkChatEvent(events)
		}
		finally
		{
			objectMapper.propertyNamingStrategy = oldNaming
		}
	}
	
	private fun parseEvent(node: JsonNode): SeChatEvent =
		when (val eventType = node["event_type"].intValue())
		{
			EventType.MessageDeleted.id -> parse<MessageDeleted>(node)
			EventType.MessageEdited.id -> parse<MessageEdited>(node)
			EventType.MessageMovedIn.id -> parse<MessageMovedIn>(node)
			EventType.MessageMovedOut.id -> parse<MessageMovedOut>(node)
			EventType.MessagePosted.id -> parse<MessagePosted>(node)
			EventType.MessageStarred.id -> parse<MessageStarred>(node)
			EventType.ReplyPosted.id -> parse<ReplyPosted>(node)
			EventType.UserJoined.id -> parse<UserJoined>(node)
			EventType.UserLeft.id -> parse<UserLeft>(node)
			EventType.UserMentioned.id -> parse<UserMentioned>(node)
			else -> throw IllegalStateException("Unknown event type '$eventType'")
		}
	
	private inline fun <reified T> parse(node: JsonNode): T =
		objectMapper.readValue(node.toString(), T::class.java)
}
