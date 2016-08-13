package kz.aphion.adverts.subscription;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import kz.aphion.adverts.subscription.listeners.RealtyAdvertSubscriptionListener;
import kz.aphion.adverts.subscription.mq.QueueNameConstants;
import kz.aphion.adverts.subscription.providers.ActiveMqProvider;

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
		logger.info("Initializing registration consumer for queue [%s]", QueueNameConstants.MQ_REALTY_ADVERTS_SUBSCRIPTION_QUEUE);
		MessageConsumer registrationConsumer = session.createConsumer(session.createQueue(QueueNameConstants.MQ_REALTY_ADVERTS_SUBSCRIPTION_QUEUE));
		mqMessageConsumers.add(registrationConsumer);
		RealtyAdvertSubscriptionListener realtyAdvertListener = new RealtyAdvertSubscriptionListener();
		mqListeners.add(realtyAdvertListener);
		registrationConsumer.setMessageListener(realtyAdvertListener);

	}
	
	
}
