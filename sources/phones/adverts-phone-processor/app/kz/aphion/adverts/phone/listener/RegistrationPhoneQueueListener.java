package kz.aphion.adverts.phone.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import play.Logger;


public class RegistrationPhoneQueueListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		try {
        	if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                Logger.info("Received: subscription id #" + text);
            } else {
            	Logger.info("Received: subscription id #" + message);
            }
        } catch (JMSException e) {
        	Logger.error(e, "");
        }
	}

}
