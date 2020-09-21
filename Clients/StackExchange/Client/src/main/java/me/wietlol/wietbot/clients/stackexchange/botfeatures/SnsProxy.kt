package me.wietlol.wietbot.clients.stackexchange.botfeatures

import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.model.PublishRequest
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.loggo.common.CommonLogger
import me.wietlol.loggo.common.EventId
import me.wietlol.loggo.common.ScopedSourceLogger
import me.wietlol.loggo.common.logError
import me.wietlol.loggo.common.logInformation
import me.wietlol.utils.common.ByteWrapper
import me.wietlol.utils.json.SimpleJsonSerializer
import me.wietlol.wietbot.clients.stackexchange.models.messages.models.MessageEventList
import me.wietlol.wietbot.clients.stackexchange.util.MessageCache
import me.wietlol.wietbot.libraries.stackexchange.chatclient.websocketclient.SeChatEvents

class SnsProxy(
	chatEvents: SeChatEvents,
	val snsClient: AmazonSNS,
	val serializer: SimpleJsonSerializer,
	val schema: Schema,
	logger: CommonLogger,
	private val stackOverflowMessageQueue: String
) : BotFeature
{
	/*
	 * on posted messages:
	 * - add message to cache
	 * - send message to sns
	 * on edited/deleted messages:
	 * - enrich data from cache
	 * - send message to sns
	 */
	
	private fun sendMessageToSns(messageEventList: MessageEventList)
	{
		val json = messageEventList
			.let { schema.serialize(it) }
			.let { ByteWrapper(it) }
			.let { serializer.serialize(it) }
		
		snsClient.publish(PublishRequest(stackOverflowMessageQueue, json))
	}
	
	private val messageCache = MessageCache()
	
	private val messagePostedEventId = EventId(1221714891, "message-posted")
	private val messageEditedEventId = EventId(772172396, "message-edited")
	private val messageDeletedEventId = EventId(270542440, "message-deleted")
	private val messagePostedErrorEventId = EventId(1681096295, "message-posted-error")
	private val messageEditedErrorEventId = EventId(1686109166, "message-edited-error")
	private val messageDeletedErrorEventId = EventId(1770320770, "message-deleted-error")
	
	init
	{
		val actualLogger = ScopedSourceLogger(logger) { it + "SnsProxy" }
		
		chatEvents.onMessagePosted.register {
			try
			{
				val message: MessageEventList = messageCache.processMessage(it)
				
				actualLogger.logInformation(messagePostedEventId, mapOf(
					"message" to message
				))
				
				sendMessageToSns(message)
				
				messageCache.cleanup()
			}
			catch (ex: Exception)
			{
				actualLogger.logError(messagePostedErrorEventId, mapOf<String, Any>(), ex)
			}
		}
		
		chatEvents.onMessageEdited.register {
			try
			{
				val message = messageCache.processMessage(it)
				
				actualLogger.logInformation(messageEditedEventId, mapOf(
					"message" to message
				))
				
				if (message != null)
					sendMessageToSns(message)
				
				messageCache.cleanup()
			}
			catch (ex: Exception)
			{
				actualLogger.logError(messageEditedErrorEventId, mapOf<String, Any>(), ex)
			}
		}
		
		chatEvents.onMessageDeleted.register {
			try
			{
				val message = messageCache.processMessage(it)
				
				actualLogger.logInformation(messageDeletedEventId, mapOf(
					"message" to message
				))
				
				if (message != null)
					sendMessageToSns(message)
				
				messageCache.cleanup()
			}
			catch (ex: Exception)
			{
				actualLogger.logError(messageDeletedErrorEventId, mapOf<String, Any>(), ex)
			}
		}
	}
}
