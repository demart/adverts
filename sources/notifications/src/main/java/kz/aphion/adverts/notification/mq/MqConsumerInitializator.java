package kz.aphion.adverts.notification.mq;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import kz.aphion.adverts.notification.listeners.NotificationChannelCallbackListener;
import kz.aphion.adverts.notification.listeners.NotificationEventListener;
import kz.aphion.adverts.notification.providers.ActiveMqProvider;

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
				
		registerQueueConsumer(session, QueueNameConstants.MQ_NOTIFICATION_QUEUE, new NotificationEventListener());
		registerQueueConsumer(session, QueueNameConstants.MQ_NOTIFICATION_CALLBACK_QUEUE, new NotificationChannelCallbackListener());

	}
	
	private static void registerQueueConsumer(Session session, String queueName, MessageListener listener) throws JMSException {
		logger.info("Initializing registration consumer for queue [%s]", queueName);
		MessageConsumer registrationConsumer = session.createConsumer(session.createQueue(queueName));
		mqMessageConsumers.add(registrationConsumer);
		mqListeners.add(listener);
		registrationConsumer.setMessageListener(listener);
	}
	
}
