package bean;

import java.util.Date;

public class Message {
	private int id;
	private int fromUserId;
	private int toUserId;
	private String message;
	private boolean isRead;
	private Date dateTime;
	
	public Message() {
		this.dateTime = new Date();
	}

	public Message(int id, int fromUserId, int toUserId, String message, boolean isRead) {
		super();
		this.id = id;
		this.fromUserId = fromUserId;
		this.toUserId = toUserId;
		this.message = message;
		this.isRead = isRead;
		this.dateTime = new Date();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(int fromUserId) {
		this.fromUserId = fromUserId;
	}

	public int getToUserId() {
		return toUserId;
	}

	public void setToUserId(int toUserId) {
		this.toUserId = toUserId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public String toString() {
		return "Message [id=" + id + ", fromUserId=" + fromUserId + ", toUserId=" + toUserId + ", message=" + message
				+ ", isRead=" + isRead + "]";
	}
}
