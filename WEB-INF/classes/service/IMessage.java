package service;

import java.util.List;

import bean.Message;

public interface IMessage {

	public int getUnreadMessagesCount(int userId);
	
	public List<Message> getSentMessages(int userId);
	
	public List<Message> getRecievedMessages(int userId);
	
	public boolean sendMessage(Message message, String subject, boolean sendEmail);
	
	public void markMessageRead(int messageId);
	
}
