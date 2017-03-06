package kz.aphion.adverts.notification.listeners;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import kz.aphion.adverts.notification.processors.NotificationEventProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс подключается к очереди callback для чтение отметок и сохранения их в БД
 * @author artem.demidovich
 *
 * Created at Aug 16, 2016
 */
public class NotificationEventListener implements MessageListener  {
	
	private static Logger logger = LoggerFactory.getLogger(NotificationEventListener.class);

	@Override
	public void onMessage(Message message) {
		try {
			Thread.currentThread().setName("NotificationListener");
        	if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                logger.trace("Received new message");
                
                new NotificationEventProcessor().processMessage(text);

                logger.trace("Processing completed");
            } else {
            	logger.warn("Received: message # " + message);
            }
        } catch (JMSException e) {
        	logger.error("JMS ERROR", e);
        } catch (Exception e) {
        	logger.error("Error during processing RealtyAdvertSubscriptionListener check request message", e);
        	try {
        		logger.error("JSON was received:\n{}", ((TextMessage)message).getText());
			} catch (JMSException e1) {
				logger.error("Error reading jms message text", e1);
			}
		}
	}


}
