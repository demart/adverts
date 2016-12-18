package kz.aphion.adverts.notification.sender.listeners;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import kz.aphion.adverts.notification.sender.processors.BrowserPushSenderProcessor;
import kz.aphion.adverts.notification.sender.processors.NotificationSenderProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrowserPushSenderListener  implements MessageListener  {
	
	private static Logger logger = LoggerFactory.getLogger(BrowserPushSenderListener.class);

	@Override
	public void onMessage(Message message) {
		try {
			Thread.currentThread().setName("BrowserPushSenderListener");
        	if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                logger.trace("Received new message");
                
                NotificationSenderProcessor processor = new BrowserPushSenderProcessor();
                processor.processMessage(text);

                logger.trace("Processing completed");
            } else {
            	logger.warn("Received: message #" + message);
            }
        } catch (JMSException e) {
        	logger.error("JMS ERROR", e);
        } catch (Exception e) {
        	logger.error("Error during processing BrowserPushSenderListener check request message", e);
        	try {
        		logger.error("JSON was received:\n%s", ((TextMessage)message).getText());
			} catch (JMSException e1) {
				logger.error("Error reading jms message text", e1);
			}
		}
	}

}