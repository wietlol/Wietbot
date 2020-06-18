package me.wietlol.wietbot.services.chatclient.core.services

import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.model.PublishRequest
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.wietbot.services.chatclient.core.interfaces.ChatRetryService
import me.wietlol.wietbot.services.chatclient.models.models.EditMessageRequest
import me.wietlol.wietbot.services.chatclient.models.models.EditMessageRetryRequest
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequest
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRetryRequest
import java.util.*

class SnsChatRetryService(
	val snsClient: AmazonSNS,
	val schema: Schema,
	val topicBaseArn: String
) : ChatRetryService
{
	override fun retrySendMessage(request: SendMessageRequest, tryCount: Int)
	{
		val newRequest = SendMessageRetryRequest.of(request, tryCount + 1)
		
		snsClient.publish(PublishRequest(
			"$topicBaseArn-retrySendMessage",
			schema.serialize(newRequest)
				.let { Base64.getEncoder().encodeToString(it) }
		))
	}
	
	override fun retryEditMessage(request: EditMessageRequest, tryCount: Int)
	{
		val newRequest = EditMessageRetryRequest.of(request, tryCount + 1)
		
		snsClient.publish(PublishRequest(
			"$topicBaseArn-retryEditMessage",
			schema.serialize(newRequest)
				.let { Base64.getEncoder().encodeToString(it) }
		))
	}
}
