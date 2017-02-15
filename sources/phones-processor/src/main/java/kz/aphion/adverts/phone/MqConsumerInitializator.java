package kz.aphion.adverts.phone;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import kz.aphion.adverts.common.MQ;
import kz.aphion.adverts.common.mq.QueueNameConstants;
import kz.aphion.adverts.phone.listener.AppExistanceCheckListener;
import kz.aphion.adverts.phone.listener.RegistrationPhoneQueueListener;
import kz.aphion.adverts.phone.processors.CheckTelegramExistanceProcessor;
import kz.aphion.adverts.phone.processors.CheckViberExistanceProcessor;
import kz.aphion.adverts.phone.processors.CheckWhatsAppExistanceProcessor;

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

		registerQueueConsumer(session, QueueNameConstants.PHONE_REGISTRATION_QUEUE.getValue(), new RegistrationPhoneQueueListener());
		registerQueueConsumer(session, QueueNameConstants.PHONE_CHECK_TELEGRAM_QUEUE.getValue(), new AppExistanceCheckListener<CheckTelegramExistanceProcessor>(CheckTelegramExistanceProcessor.class));
		registerQueueConsumer(session, QueueNameConstants.PHONE_CHECK_VIBER_QUEUE.getValue(), new AppExistanceCheckListener<CheckViberExistanceProcessor>(CheckViberExistanceProcessor.class));
		registerQueueConsumer(session, QueueNameConstants.PHONE_CHECK_WHATSAPP_QUEUE.getValue(), new AppExistanceCheckListener<CheckWhatsAppExistanceProcessor>(CheckWhatsAppExistanceProcessor.class));
	}
	
	private static void registerQueueConsumer(Session session, String queueName, MessageListener listener) throws JMSException {
		logger.info("Initializing registration consumer for queue [{}]", queueName);
		MessageConsumer registrationConsumer = session.createConsumer(session.createQueue(queueName));
		mqMessageConsumers.add(registrationConsumer);
		mqListeners.add(listener);
		registrationConsumer.setMessageListener(listener);
	}
	
}
