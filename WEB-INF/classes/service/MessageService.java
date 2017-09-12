package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.smartcardio.CommandAPDU;
import javax.sql.rowset.CachedRowSet;

import bean.Advertisement;
import bean.Message;
import bean.User;
import dbutil.DataManager;
import util.CommonUtils;

public class MessageService implements IMessage {

private DataManager dataManager = null;
	
	public MessageService() {
		dataManager = new DataManager();
	}
	
	@Override
	public int getUnreadMessagesCount(int userId) {
		int count = -1;
		CachedRowSet row = dataManager.getUnreadMessagesCount(userId);
		try {
			if (row != null) {
				if (row.next()) {
					count = row.getInt("count");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<Message> getSentMessages(int userId) {
		List<Message> messages = new ArrayList<>();
		CachedRowSet row = dataManager.getSentMessages(userId);
		try {
			if (row != null) {
				while (row.next()) {
					Message message = new Message(row.getInt("id"), row.getInt("fromUserId"), row.getInt("toUserId"), row.getString("message"), row.getBoolean("isRead"));
					messages.add(message);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}

	@Override
	public List<Message> getRecievedMessages(int userId) {
		List<Message> messages = new ArrayList<>();
		CachedRowSet row = dataManager.getReceivedMessages(userId);
		try {
			if (row != null) {
				while (row.next()) {
					Message message = new Message(row.getInt("id"), row.getInt("fromUserId"), row.getInt("toUserId"), row.getString("message"), row.getBoolean("isRead"));
					messages.add(message);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}
	
	@Override
	public boolean sendMessage(Message message, String subject, boolean sendEmail) {
		dataManager.addMessage(message);
		UserService service = new UserService();
		//User from = service.getUser(message.getFromUserId());
		User to = service.getUser(message.getToUserId());
		if (sendEmail)
			CommonUtils.sendEmail(to.getEmail(), subject, message.getMessage());
		return true;
	}

	@Override
	public void markMessageRead(int messageId) {
		dataManager.updateMessageReadStatus(messageId, true);
	}
	
	public boolean contactSeller(int advertisementId, User user, String userMessage, boolean specifyEmail, boolean specifyPhone) {
		StringBuilder message = new StringBuilder(); 
		Advertisement ad = (new AdvertisementService()).getAdvertisement(advertisementId);
		message.append(String.format("<p>Dear %s,</p>", ad.getPostedBy().getFirstName()));
		message.append(String.format("<p>A user is interested in your ad <b>%s</b>. The details of the user is - </p>", ad.getTitle()));
		message.append(String.format("<p>Name: %s %s <br>", user.getFirstName(), user.getLastName()));
		if (specifyEmail)
			message.append(String.format("Email: %s <br>", user.getEmail()));
		
		if (specifyPhone)
			message.append(String.format("Phone: %s <br>", user.getContactNumber()));
		message.append("</p>");
		
		if (userMessage != null && !userMessage.isEmpty()) {
			message.append("<p><b>USER MESSAGE:</b><br>");
			message.append("---------------------------------------------------------------<br>");
			message.append(String.format("%s <br>", userMessage));
			message.append("---------------------------------------------------------------</p>");
		}
		
		message.append("<p>Thanks, <br>BuyOrSell.com</p>");
		
		Message msg = new Message((new Random()).nextInt(Integer.MAX_VALUE), user.getId(), ad.getPostedBy().getId(), message.toString(), false);
		sendMessage(msg, "A user is interested in your Ad.", true);
		return true;
	}

	public static void main(String[] args) {
		// Adding sample messages
		for (int i = 1; i <= 5; i++) {
			Message message = new Message((new Random()).nextInt(Integer.MAX_VALUE), 2, 1, 
					"sample message. sample message. sample message. sample message. sample message. sample message. sample message.", false);
			(new MessageService()).sendMessage(message, "sample message.", false);
		}
	}

}
