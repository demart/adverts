package kz.aphion.adverts.phone.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import kz.aphion.adverts.phone.processors.CheckTelegramExistanceProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listener для получения сообщений из очереди для проверки наличие Telegram приложения
 * @author artem.demidovich
 *
 */
public class TelegramAppExistanceCheckListener implements MessageListener {

	private static Logger logger = LoggerFactory.getLogger(TelegramAppExistanceCheckListener.class);
		
	@Override
	public void onMessage(Message message) {
		Thread.currentThread().setName("TelegramAppCheckQListener");
		try {
        	if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                logger.info("Received new message");
                
                CheckTelegramExistanceProcessor processor = new CheckTelegramExistanceProcessor();
            	processor.processMessage(text);

            	logger.info("Processing completed");
            } else {
            	logger.info("Received: subscription id #" + message);
            }
        	
        } catch (JMSException e) {
        	logger.error("", e);
        } catch (Exception e) {
        	logger.error("Error during processing viber check request message", e);
        	try {
				logger.error("JSON was received:\n{}", ((TextMessage)message).getText());
			} catch (JMSException e1) {
				e1.printStackTrace();
			}
		}
	}

}
