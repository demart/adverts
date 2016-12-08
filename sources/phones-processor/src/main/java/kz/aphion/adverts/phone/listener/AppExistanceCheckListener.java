package kz.aphion.adverts.phone.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kz.aphion.adverts.phone.processors.AbstractAppExistanceProcessor;

public class AppExistanceCheckListener<T extends AbstractAppExistanceProcessor> implements MessageListener  {

	private static Logger logger = LoggerFactory.getLogger(AppExistanceCheckListener.class);

	protected Class<T> classForT;
	
	public AppExistanceCheckListener(Class<T> clazz) {
		  this.classForT=clazz;
	}
	
	@Override
	public void onMessage(Message message) {
		try {
			Thread.currentThread().setName(classForT.getName() + "QListener");
        	if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                logger.info("Received new message");
                
                AbstractAppExistanceProcessor processor = classForT.newInstance();
            	processor.processMessage(text);

            	logger.info("Processing completed");
            } else {
            	logger.info("Received: subscription id #" + message);
            }
        	
        } catch (JMSException e) {
        	logger.error("",e);
        } catch (Exception e) {
        	logger.error("Error during processing " + classForT.getName() + " check request message", e);
        	try {
        		logger.error("JSON was received:\n{}", ((TextMessage)message).getText());
			} catch (JMSException e1) {
				e1.printStackTrace();
			}
		}
	}
}
