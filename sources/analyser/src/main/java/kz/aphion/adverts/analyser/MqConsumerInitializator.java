package kz.aphion.adverts.analyser;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import kz.aphion.adverts.analyser.listeners.RealtyAdvertListener;
import kz.aphion.adverts.analyser.mq.QueueNameConstants;
import kz.aphion.adverts.analyser.providers.ActiveMqProvider;

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
		logger.info("Initializing registration consumer for queue [%s]", QueueNameConstants.MQ_REALTY_ADVERTS_QUEUE);
		MessageConsumer registrationConsumer = session.createConsumer(session.createQueue(QueueNameConstants.MQ_REALTY_ADVERTS_QUEUE));
		mqMessageConsumers.add(registrationConsumer);
		RealtyAdvertListener realtyAdvertListener = new RealtyAdvertListener();
		mqListeners.add(realtyAdvertListener);
		registrationConsumer.setMessageListener(realtyAdvertListener);

	}
	
	
}
