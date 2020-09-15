package me.wietlol.wietbot.services.simplechatclient.core.api.models;

public class SendMessageRequest
{
	private int roomId = 0;
	private String messageText = null;
	
	public int getRoomId()
	{
		return roomId;
	}
	
	public void setRoomId(int roomId)
	{
		this.roomId = roomId;
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
