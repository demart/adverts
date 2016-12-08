package kz.aphion.adverts.phone.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kz.aphion.adverts.phone.processors.RegistrationPhoneProcessor;


public class RegistrationPhoneQueueListener implements MessageListener {

	private static Logger logger = LoggerFactory.getLogger(RegistrationPhoneQueueListener.class);

	@Override
	public void onMessage(Message message) {
		Thread.currentThread().setName("RegPhoneQListener");
		try {
        	if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                logger.info("Received new message");
                
                RegistrationPhoneProcessor processor = new RegistrationPhoneProcessor();
            	processor.processRegistrationMessage(text);

            	logger.info("Processing completed");
            } else {
            	logger.info("Received: subscription id #" + message);
            }
        	
        } catch (JMSException e) {
        	logger.error("", e);
        } catch (Exception e) {
        	logger.error("Error during processing registration phone message", e);
        	try {
				logger.error("JSON was received:\n{}", ((TextMessage)message).getText());
			} catch (JMSException e1) {
				e1.printStackTrace();
			}
		}
	}

}
