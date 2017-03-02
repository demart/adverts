package kz.aphion.adverts.subscription;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import kz.aphion.adverts.common.MQ;
import kz.aphion.adverts.common.mq.QueueNameConstants;
import kz.aphion.adverts.subscription.listeners.RealtyAdvertSubscriptionListener;
import kz.aphion.adverts.subscription.listeners.SubscriptionNotificationBuilderListener;

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

		registerQueueConsumer(session, QueueNameConstants.ADVERTS_REALTY_SUBSCRIPTION_QUEUE.getValue(), new RealtyAdvertSubscriptionListener());
		registerQueueConsumer(session, QueueNameConstants.ADVERTS_SUBSCRIPTION_NOTIFICATION_BUILDER_QUEUE.getValue(), new SubscriptionNotificationBuilderListener());

		// Запускаем очереди
		MQ.INSTANCE.getConnection().start();
	}
	
	private static void registerQueueConsumer(Session session, String queueName, MessageListener listener) throws JMSException {
		logger.info("Initializing registration consumer for queue [{}]", queueName);
		MessageConsumer registrationConsumer = session.createConsumer(session.createQueue(queueName));
		mqMessageConsumers.add(registrationConsumer);
		mqListeners.add(listener);
		registrationConsumer.setMessageListener(listener);
	}
	
}
