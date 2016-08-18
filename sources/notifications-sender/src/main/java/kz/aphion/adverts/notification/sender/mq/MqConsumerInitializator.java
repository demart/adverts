package kz.aphion.adverts.notification.sender.mq;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import kz.aphion.adverts.notification.sender.listeners.BrowserPushSenderListener;
import kz.aphion.adverts.notification.sender.listeners.EmailSenderListener;
import kz.aphion.adverts.notification.sender.listeners.PushSenderListener;
import kz.aphion.adverts.notification.sender.listeners.SmsSenderListener;
import kz.aphion.adverts.notification.sender.providers.ActiveMqProvider;

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
		Session session =  ActiveMqProvider.getInstance().getSession();

		registerQueueConsumer(session, QueueNameConstants.MQ_NOTIFICATION_BROWSER_QUEUE, new BrowserPushSenderListener());
		registerQueueConsumer(session, QueueNameConstants.MQ_NOTIFICATION_EMAIL_QUEUE, new EmailSenderListener());
		registerQueueConsumer(session, QueueNameConstants.MQ_NOTIFICATION_PUSH_QUEUE, new PushSenderListener());
		registerQueueConsumer(session, QueueNameConstants.MQ_NOTIFICATION_SMS_QUEUE, new SmsSenderListener());		
	}
	
	
	private static void registerQueueConsumer(Session session, String queueName, MessageListener listener) throws JMSException {
		logger.info("Initializing registration consumer for queue [%s]", queueName);
		MessageConsumer registrationConsumer = session.createConsumer(session.createQueue(queueName));
		mqMessageConsumers.add(registrationConsumer);
		mqListeners.add(listener);
		registrationConsumer.setMessageListener(listener);
	}
	
}
