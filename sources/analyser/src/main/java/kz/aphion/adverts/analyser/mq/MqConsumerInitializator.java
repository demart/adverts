package kz.aphion.adverts.analyser.mq;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import kz.aphion.adverts.analyser.listeners.RealtyAdvertListener;
import kz.aphion.adverts.common.MQ;
import kz.aphion.adverts.common.mq.QueueNameConstants;

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
				
		// Запускаем Listener по обработке объявлений о недвижимости
		logger.info("Initializing registration consumer for queue [{}]", QueueNameConstants.ADVERTS_ANALYSE_QUEUE.getValue());
		MessageConsumer registrationConsumer = session.createConsumer(session.createQueue(QueueNameConstants.ADVERTS_ANALYSE_QUEUE.getValue()));
		mqMessageConsumers.add(registrationConsumer);
		RealtyAdvertListener realtyAdvertListener = new RealtyAdvertListener();
		mqListeners.add(realtyAdvertListener);
		registrationConsumer.setMessageListener(realtyAdvertListener);

	}
	
	
}
