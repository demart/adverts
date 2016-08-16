package kz.aphion.adverts.notification.mq;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import kz.aphion.adverts.notification.listeners.NotificationSubscriptionListener;
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
				
		// Запускаем Listener по обработке объявлений о недвижимости
		logger.info("Initializing registration consumer for queue [%s]", QueueNameConstants.MQ_ADVERTS_IMMEDIATE_NOTIFICATION_QUEUE);
		MessageConsumer registrationConsumer = session.createConsumer(session.createQueue(QueueNameConstants.MQ_ADVERTS_IMMEDIATE_NOTIFICATION_QUEUE));
		mqMessageConsumers.add(registrationConsumer);
		NotificationSubscriptionListener realtyAdvertListener = new NotificationSubscriptionListener();
		mqListeners.add(realtyAdvertListener);
		registrationConsumer.setMessageListener(realtyAdvertListener);

	}
	
	
}
