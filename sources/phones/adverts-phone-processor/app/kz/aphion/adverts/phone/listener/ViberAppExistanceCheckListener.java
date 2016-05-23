package kz.aphion.adverts.phone.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import kz.aphion.adverts.phone.processors.CheckViberExistanceProcessor;
import play.Logger;

/**
 * Listener для получения сообщений из очереди для проверки наличие Viber приложения
 * @author artem.demidovich
 *
 */
public class ViberAppExistanceCheckListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		Thread.currentThread().setName("ViberAppCheckQListener");
		try {
        	if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                Logger.info("Received new message");
                
                CheckViberExistanceProcessor processor = new CheckViberExistanceProcessor();
            	processor.processMessage(text);

            	Logger.info("Processing completed");
            } else {
            	Logger.info("Received: subscription id #" + message);
            }
        	
        } catch (JMSException e) {
        	Logger.error(e, "");
        } catch (Exception e) {
        	Logger.error(e, "Error during processing viber check request message");
        	try {
				Logger.error("JSON was received:\n%s", ((TextMessage)message).getText());
			} catch (JMSException e1) {
				e1.printStackTrace();
			}
		}
	}

}
