package kz.aphion.adverts.notification.sender.processors;

import javax.jms.JMSException;

import kz.aphion.adverts.notification.mq.models.NotificationStatus;
import kz.aphion.adverts.notification.mq.models.channel.NotificationChannelMessage;
import kz.aphion.adverts.notification.sender.utils.CallbackUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsSenderProcessor implements NotificationSenderProcessor {

	private static Logger logger = LoggerFactory.getLogger(SmsSenderProcessor.class);
	
	@Override
	public void processMessage(String message) throws JMSException, Exception {
		logger.info("Sending new SMS...");
		
		NotificationChannelMessage notificationMessage = NotificationChannelMessage.parseModel(message);
		if (notificationMessage == null) {
			logger.error("Error parsing NotificationChannelMessage object, processing will be skipped.");
			return;
		}
		
		logger.info("Simulating sending SMS...");
		CallbackUtils.sendCallbackNotification(notificationMessage,NotificationStatus.SENT);
		logger.info("Simulating receiving Ok delivery status.");
		CallbackUtils.sendCallbackNotification(notificationMessage,NotificationStatus. DELIVERED);
		
		logger.info("SMS sent.");
	}

	
}