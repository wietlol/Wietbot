package me.wietlol.wietbot.services.chatclient.core.services

import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.model.PublishRequest
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.utils.common.ByteWrapper
import me.wietlol.utils.json.SimpleJsonSerializer
import me.wietlol.wietbot.services.chatclient.core.interfaces.ChatRetryService
import me.wietlol.wietbot.services.chatclient.models.models.EditMessageRequest
import me.wietlol.wietbot.services.chatclient.models.models.EditMessageRetryRequestImpl
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRequest
import me.wietlol.wietbot.services.chatclient.models.models.SendMessageRetryRequestImpl

class SnsChatRetryService(
	val snsClient: AmazonSNS,
	val schema: Schema,
	val json: SimpleJsonSerializer,
	val topicBaseArn: String,
) : ChatRetryService
{
	override fun retrySendMessage(request: SendMessageRequest, tryCount: Int)
	{
		val newRequest = SendMessageRetryRequestImpl(request, tryCount + 1)
		
		snsClient.publish(PublishRequest(
			"$topicBaseArn-retrySendMessage",
			schema.serialize(newRequest)
				.let { ByteWrapper(it) }
				.let { json.serialize(it) }
		))
	}
	
	override fun retryEditMessage(request: EditMessageRequest, tryCount: Int)
	{
		val newRequest = EditMessageRetryRequestImpl(request, tryCount + 1)
		
		snsClient.publish(PublishRequest(
			"$topicBaseArn-retryEditMessage",
			schema.serialize(newRequest)
				.let { ByteWrapper(it) }
				.let { json.serialize(it) }
		))
	}
}
