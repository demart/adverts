package kz.aphion.adverts.phone.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import kz.aphion.adverts.phone.processors.CheckWhatsAppExistanceProcessor;
import play.Logger;

/**
 * Listener для получения сообщений из очереди для проверки наличие Whatsapp приложения
 * @author artem.demidovich
 *
 */
public class WhatsappAppExistanceCheckListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		Thread.currentThread().setName("WhatsAppCheckQListener");
		try {
        	if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                Logger.info("Received new message");
                
                CheckWhatsAppExistanceProcessor processor = new CheckWhatsAppExistanceProcessor();
            	processor.processMessage(text);

            	Logger.info("Processing completed");
            } else {
            	Logger.info("Received: subscription id #" + message);
            }
        	
        } catch (JMSException e) {
        	Logger.error(e, "");
        } catch (Exception e) {
        	Logger.error(e, "Error during processing whatsapp check request message");
        	try {
				Logger.error("JSON was received:\n%s", ((TextMessage)message).getText());
			} catch (JMSException e1) {
				e1.printStackTrace();
			}
		}
	}

}
