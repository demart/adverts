package kz.aphion.adverts.phone.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import kz.aphion.adverts.phone.processors.AbstractAppExistanceProcessor;
import play.Logger;

public class AppExistanceCheckListener<T extends AbstractAppExistanceProcessor> implements MessageListener  {

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
                Logger.info("Received new message");
                
                AbstractAppExistanceProcessor processor = classForT.newInstance();
            	processor.processMessage(text);

            	Logger.info("Processing completed");
            } else {
            	Logger.info("Received: subscription id #" + message);
            }
        	
        } catch (JMSException e) {
        	Logger.error(e, "");
        } catch (Exception e) {
        	Logger.error(e, "Error during processing " + classForT.getName() + " check request message");
        	try {
				Logger.error("JSON was received:\n%s", ((TextMessage)message).getText());
			} catch (JMSException e1) {
				e1.printStackTrace();
			}
		}
	}
}
