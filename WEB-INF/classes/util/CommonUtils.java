package util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class CommonUtils {
	/* <Pavithra> */
	public static final int ADS_PER_PAGE = 9; 
	/* </Pavithra> */
	
	public static void sendEmail(String to, String subject, String msg) {
		final String fromUsername = "BuyOrSell.iit@gmail.com";
		final String fromPassword = "BuyOrSell@5";

		// Get the session object
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromUsername, fromPassword);
			}
		});

		// Compose the message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromUsername));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setContent(msg, "text/html; charset=utf-8");

			// send the message
			Transport.send(message);
			System.out.println("message sent successfully...");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isAdvertisementNew(Date postedOn) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		c.add(Calendar.DATE, 3);
		Date newDate = c.getTime();
		return postedOn.after(newDate);
	}
}
