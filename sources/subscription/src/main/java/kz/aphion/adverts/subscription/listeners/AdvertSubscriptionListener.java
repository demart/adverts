package kz.aphion.adverts.subscription.listeners;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import kz.aphion.adverts.subscription.processors.AdvertSubscriptionProcessor;
import kz.aphion.adverts.subscription.processors.AdvertSubscriptionProcessorImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс листенер слушает очередь сообщений о недивжимости
 * 
 * @author artem.demidovich
 *
 * Created at Jun 12, 2016
 */
public class AdvertSubscriptionListener implements MessageListener  {
	
	private static Logger logger = LoggerFactory.getLogger(ApplicationStartupListener.class);

	@Override
	public void onMessage(Message message) {
		try {
			Thread.currentThread().setName("AdvertListener");
        	if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                logger.trace("Received new message:\n {}", text);
                
                AdvertSubscriptionProcessor processor = new AdvertSubscriptionProcessorImpl();
                processor.processMessage(text);

                logger.trace("Processing completed");
            } else {
            	logger.warn("Received: message # {}", message);
            }
        } catch (JMSException e) {
        	logger.error("JMS ERROR", e);
        } catch (Exception e) {
        	logger.error("Error during processing AdvertSubscriptionListener check request message", e);
        	try {
        		logger.error("JSON was received:\n{}", ((TextMessage)message).getText());
			} catch (JMSException e1) {
				logger.error("Error reading jms message text", e1);
			}
		}
	}

}
