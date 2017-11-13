package kz.aphion.adverts.notification.sender.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendEmailUtils {

	private static final Logger logger = LoggerFactory.getLogger(SendEmailUtils.class);
	
	public static Session createSession(String host, Integer port, boolean secured, final String email, final String password) {
		Properties props = new Properties();
		props.put("mail.smtp.host", host); //SMTP Host     outlook.office365.com  smtp.gmail.com    
		props.put("mail.smtp.port", port); //TLS Port 587
		props.put("mail.smtp.auth", "true"); //enable authentication
		props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
		
		//create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email, password);
			}
		};
		
		Session session = Session.getInstance(props, auth);
		
		return session;
	}
	
	
	/**
	 * Prepare email to be sent
	 * 
	 * @param emailFrom
	 * @param emailTo
	 * @param body
	 * @param subject
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public static MimeMessage getEmailMessage(String emailFrom, String emailTo, String body, String subject, Session session) throws Exception {
		MimeMessage msg = new MimeMessage(session);
		//set message headers
		msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
		msg.addHeader("format", "flowed");
		msg.addHeader("Content-Transfer-Encoding", "8bit");

		msg.setFrom(new InternetAddress(emailFrom, "no-reply"));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo, false));
		msg.setSentDate(new Date());
		
		msg.setSubject(subject, "UTF-8");
		//msg.setText(body, "UTF-8");
		msg.setContent(body, "text/html; charset=utf-8");
		
		return msg;
	}
	
	
}
