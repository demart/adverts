package kz.aphion.adverts.subscription.listeners;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import kz.aphion.adverts.subscription.processors.AdvertSubscriptionProcessor;
import kz.aphion.adverts.subscription.processors.RealtyAdvertSubscriptionProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс листенер слушает очередь сообщений о недивжимости
 * 
 * @author artem.demidovich
 *
 * Created at Jun 12, 2016
 */
public class SubscriptionNotificationBuilderListener implements MessageListener  {
	
	private static Logger logger = LoggerFactory.getLogger(ApplicationStartupListener.class);

	@Override
	public void onMessage(Message message) {
		try {
			Thread.currentThread().setName("SubNotEvtBuilder");
        	if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                //logger.info("Received new message");
                
                AdvertSubscriptionProcessor processor = new RealtyAdvertSubscriptionProcessor();
                processor.processMessage(text);

                //logger.info("Processing completed");
            } else {
            	logger.warn("Received: message #" + message);
            }
        } catch (JMSException e) {
        	logger.error("JMS ERROR", e);
        } catch (Exception e) {
        	logger.error("Error during processing RealtyAdvertSubscriptionListener check request message", e);
        	try {
        		logger.error("JSON was received:\n%s", ((TextMessage)message).getText());
			} catch (JMSException e1) {
				logger.error("Error reading jms message text", e1);
			}
		}
	}

}
