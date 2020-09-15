package me.wietlol.wietbot.services.simplechatclient.core.api.models;

public class EditMessageRequest
{
	private int messageId = 0;
	private String messageText = null;
	
	public int getMessageId()
	{
		return messageId;
	}
	
	public void setMessageId(int messageId)
	{
		this.messageId = messageId;
	}
	
	public String getMessageText()
	{
		return messageText;
	}
	
	public void setMessageText(String messageText)
	{
		this.messageText = messageText;
	}
}
