package kz.aphion.adverts.notification.sender.mq;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import kz.aphion.adverts.common.MQ;
import kz.aphion.adverts.common.mq.QueueNameConstants;
import kz.aphion.adverts.notification.sender.listeners.BrowserPushSenderListener;
import kz.aphion.adverts.notification.sender.listeners.EmailSenderListener;
import kz.aphion.adverts.notification.sender.listeners.PushSenderListener;
import kz.aphion.adverts.notification.sender.listeners.SmsSenderListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс инициализатор Listeners на очереди MQ сообщений
 * @author artem.demidovich
 *
 */
public class MqConsumerInitializator {

	private static Logger logger = LoggerFactory.getLogger(MqConsumerInitializator.class);
	
	public static List<MessageConsumer> mqMessageConsumers = new ArrayList<MessageConsumer>();
	public static List<MessageListener> mqListeners = new ArrayList<MessageListener>();
	
	public static void initListeners() throws JMSException, Exception {
		Session session =  MQ.INSTANCE.getSession();

		registerQueueConsumer(session, QueueNameConstants.NOTIFICATION_BROWSER_QUEUE.getValue(), new BrowserPushSenderListener());
		registerQueueConsumer(session, QueueNameConstants.NOTIFICATION_EMAIL_QUEUE.getValue(), new EmailSenderListener());
		registerQueueConsumer(session, QueueNameConstants.NOTIFICATION_PUSH_QUEUE.getValue(), new PushSenderListener());
		registerQueueConsumer(session, QueueNameConstants.NOTIFICATION_SMS_QUEUE.getValue(), new SmsSenderListener());		
	}
	
	
	private static void registerQueueConsumer(Session session, String queueName, MessageListener listener) throws JMSException {
		logger.info("Initializing registration consumer for queue [{}]", queueName);
		MessageConsumer registrationConsumer = session.createConsumer(session.createQueue(queueName));
		mqMessageConsumers.add(registrationConsumer);
		mqListeners.add(listener);
		registrationConsumer.setMessageListener(listener);
	}
	
}
