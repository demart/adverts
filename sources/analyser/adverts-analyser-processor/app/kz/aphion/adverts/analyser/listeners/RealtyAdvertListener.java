package kz.aphion.adverts.analyser.listeners;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import kz.aphion.adverts.analyser.processors.RealtyAdvertAnalyserProcessor;
import play.Logger;

/**
 * Класс листенер слушает очередь сообщений о недивжимости
 * 
 * @author artem.demidovich
 *
 * Created at Jun 12, 2016
 */
public class RealtyAdvertListener implements MessageListener  {
	
	@Override
	public void onMessage(Message message) {
		try {
			Thread.currentThread().setName("RealtyAdvertListener");
        	if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                Logger.info("Received new message");
                
                RealtyAdvertAnalyserProcessor processor = new RealtyAdvertAnalyserProcessor();
                processor.processMessage(text);

            	Logger.info("Processing completed");
            } else {
            	Logger.info("Received: subscription id #" + message);
            }
        } catch (JMSException e) {
        	Logger.error(e, "");
        } catch (Exception e) {
        	Logger.error(e, "Error during processing RealtyAdvertListener check request message");
        	try {
				Logger.error("JSON was received:\n%s", ((TextMessage)message).getText());
			} catch (JMSException e1) {
				e1.printStackTrace();
			}
		}
	}

}
