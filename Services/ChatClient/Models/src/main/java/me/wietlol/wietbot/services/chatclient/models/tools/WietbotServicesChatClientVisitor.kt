package me.wietlol.wietbot.services.chatclient.models.tools

import me.wietlol.wietbot.services.chatclient.models.models.*

interface WietbotServicesChatClientVisitor
{
	fun visit(sendMessageRequest: SendMessageRequest)
	{
		accept(sendMessageRequest)
	}
	
	fun visit(sendMessageRetryRequest: SendMessageRetryRequest)
	{
		accept(sendMessageRetryRequest)
		
		sendMessageRetryRequest.request.also { visit(it) }
	}
	
	fun visit(sendMessageResponse: SendMessageResponse)
	{
		accept(sendMessageResponse)
	}
	
	fun visit(editMessageRequest: EditMessageRequest)
	{
		accept(editMessageRequest)
	}
	
	fun visit(editMessageRetryRequest: EditMessageRetryRequest)
	{
		accept(editMessageRetryRequest)
		
		editMessageRetryRequest.request.also { visit(it) }
	}
	
	fun visit(editMessageResponse: EditMessageResponse)
	{
		accept(editMessageResponse)
	}
	
	fun accept(sendMessageRequest: SendMessageRequest) {}
	fun accept(sendMessageRetryRequest: SendMessageRetryRequest) {}
	fun accept(sendMessageResponse: SendMessageResponse) {}
	fun accept(editMessageRequest: EditMessageRequest) {}
	fun accept(editMessageRetryRequest: EditMessageRetryRequest) {}
	fun accept(editMessageResponse: EditMessageResponse) {}
}
