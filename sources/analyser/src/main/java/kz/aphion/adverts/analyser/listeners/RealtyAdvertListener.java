package kz.aphion.adverts.analyser.listeners;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import kz.aphion.adverts.analyser.processors.AdvertAnalyserProcessor;
import kz.aphion.adverts.analyser.processors.AdvertAnalyserProcessorImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс листенер слушает очередь сообщений о недивжимости
 * 
 * @author artem.demidovich
 *
 * Created at Jun 12, 2016
 */
public class RealtyAdvertListener implements MessageListener  {
	
	private static Logger logger = LoggerFactory.getLogger(ApplicationStartupListener.class);

	@Override
	public void onMessage(Message message) {
		try {
			Thread.currentThread().setName("REALTY-ANALYSER");
        	if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                logger.debug("Received new message");
                
                AdvertAnalyserProcessor processor = new AdvertAnalyserProcessorImpl();
                processor.processMessage(text);

                logger.debug("Processing completed");
            } else {
            	logger.warn("Received: subscription id #" + message);
            }
        } catch (JMSException e) {
        	logger.error("JMS ERROR", e);
        } catch (Exception e) {
        	logger.error("Error during processing RealtyAdvertListener check request message", e);
        	try {
        		logger.error("JSON was received:\n{}", ((TextMessage)message).getText());
			} catch (JMSException e1) {
				logger.error("Error reading jms message text", e1);
			}
		}
	}

}
