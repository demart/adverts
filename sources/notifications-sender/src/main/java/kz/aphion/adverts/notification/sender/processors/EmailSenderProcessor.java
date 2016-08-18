package kz.aphion.adverts.notification.sender.processors;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.jms.JMSException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import kz.aphion.adverts.notification.sender.mq.models.NotificationChannelMessage;
import kz.aphion.adverts.notification.sender.mq.models.NotificationStatus;
import kz.aphion.adverts.notification.sender.utils.CallbackUtils;



import kz.aphion.adverts.notification.sender.utils.SendEmailUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailSenderProcessor implements NotificationSenderProcessor {

	private static Logger logger = LoggerFactory.getLogger(EmailSenderProcessor.class);

	private final static String EMAIL_ADDRESS_FROM_PARAMETER = "notifications.sender.email.address.from";
	private final static String EMAIL_SERVER_IP_PARAMETER = "notifications.sender.email.server.ip";
	private final static String EMAIL_SERVER_PORT_PARAMETER = "notifications.sender.email.server.port";
	private final static String EMAIL_SERVER_SECURED_CONNECTION_PARAMETER = "notifications.sender.email.server.secured";
	private final static String EMAIL_SERVER_USERNAME_PARAMETER = "notifications.sender.email.server.username";
	private final static String EMAIL_SERVER_PASSWORD_PARAMETER = "notifications.sender.email.server.password";

	private static Boolean emailServerSecuredConnection = false;	
	private static String emailSMTPHost;
	private static Integer emailSMTPPort;
	private static String emailSMTPUsername;
	private static String emailSMTPFromEmail;
	private static String emailSMTPEmailPassword;


	static {
		logger.info("Reading configuration for email message processor...");
		try {
			InitialContext ic = new InitialContext();

			emailSMTPHost = (String)ic.lookup("java:comp/env/" + EMAIL_SERVER_IP_PARAMETER);
			logger.info("Processor email server host is set: " + emailSMTPHost);

			emailSMTPPort = (Integer)ic.lookup("java:comp/env/" + EMAIL_SERVER_PORT_PARAMETER);
			logger.info("Processor email server port is set: " + emailSMTPPort);

			emailSMTPFromEmail = (String)ic.lookup("java:comp/env/" + EMAIL_ADDRESS_FROM_PARAMETER);
			logger.info("Processor email address from is set: " + emailSMTPFromEmail);

			emailSMTPUsername = (String)ic.lookup("java:comp/env/" + EMAIL_SERVER_USERNAME_PARAMETER);
			logger.info("Processor email username from is set: " + emailSMTPUsername);

			emailSMTPEmailPassword = (String)ic.lookup("java:comp/env/" + EMAIL_SERVER_PASSWORD_PARAMETER);
			logger.info("Processor email address from is set: " + emailSMTPEmailPassword);

			emailServerSecuredConnection = (Boolean)ic.lookup("java:comp/env/" + EMAIL_SERVER_SECURED_CONNECTION_PARAMETER);
			logger.info("Processor email server secured connection is set: " + emailServerSecuredConnection);

		} catch (NamingException e) {
			logger.error("FATAL! Error reading configuration for Sending emails. Maybe missing configuration.", e);
		}
	}
	
	@Override
	public void processMessage(String message) throws JMSException, Exception {
		logger.info("Sending new Email...");
		
		// Parse model
		NotificationChannelMessage notificationMessage = NotificationChannelMessage.parseModel(message);
		if (notificationMessage == null) {
			logger.error("Error parsing NotificationChannelMessage object. Message will be skipped with error callback.");
			CallbackUtils.sendCallbackNotification(notificationMessage, NotificationStatus.FAILED, "Error parsing NotificationChannelMessage object.");
			return;
		}
		
		// Create session to send emails
		Session session = SendEmailUtils.createSession(emailSMTPHost, emailSMTPPort, emailServerSecuredConnection, emailSMTPFromEmail, emailSMTPEmailPassword);
		if (session == null) {
			logger.error("Error trying to create javax Mail session. Message will be skipped with error callback.");
			CallbackUtils.sendCallbackNotification(notificationMessage, NotificationStatus.FAILED, "Error trying to create javax Mail session.");
			return;
		}
		
		// Create mail message
		String targetEmail = notificationMessage.addreseeId;
		String subject = notificationMessage.title;
		String content = notificationMessage.content;
		
		try {
			
			MimeMessage emailMessage = SendEmailUtils.getEmailMessage(emailSMTPFromEmail, targetEmail, content, subject, session);
			Transport.send(emailMessage);
			logger.debug("Email to {} was successfully sent", targetEmail);
			
			CallbackUtils.sendCallbackNotification(notificationMessage, NotificationStatus.SENT);
			
		} catch (Exception ex) {
			logger.error("Error sending emailMessage. Message will be skipped with error callback", ex);
			CallbackUtils.sendCallbackNotification(notificationMessage, NotificationStatus.FAILED, ex.getMessage());
			return;
		}
	}

}
