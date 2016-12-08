package kz.aphion.adverts.phone;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import kz.aphion.adverts.phone.listener.AppExistanceCheckListener;
import kz.aphion.adverts.phone.listener.RegistrationPhoneQueueListener;
import kz.aphion.adverts.phone.mq.QueueNameConstants;
import kz.aphion.adverts.phone.processors.CheckTelegramExistanceProcessor;
import kz.aphion.adverts.phone.processors.CheckViberExistanceProcessor;
import kz.aphion.adverts.phone.processors.CheckWhatsAppExistanceProcessor;
import kz.aphion.adverts.phone.providers.ActiveMqProvider;

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

		/*
		// Запускаем Listener по регистрации телефонов
		logger.info("Initializing registration consumer for queue [{}]", QueueNameConstants.MQ_REGISTRATION_QUEUE);
		MessageConsumer registrationConsumer = session.createConsumer(session.createQueue(QueueNameConstants.MQ_REGISTRATION_QUEUE));
		mqMessageConsumers.add(registrationConsumer);
		RegistrationPhoneQueueListener registrationListener = new RegistrationPhoneQueueListener();
		mqListeners.add(registrationListener);
		registrationConsumer.setMessageListener(registrationListener);
		
		// Запускаем Listener
		logger.info("Initializing check application existance on Telegram consumer for queue [{}]", QueueNameConstants.MQ_CHECK_TELEGRAM_QUEUE);
		MessageConsumer telegramCheckConsumer = session.createConsumer(session.createQueue(QueueNameConstants.MQ_CHECK_TELEGRAM_QUEUE));
		mqMessageConsumers.add(telegramCheckConsumer);
		AppExistanceCheckListener<CheckTelegramExistanceProcessor> telegramCheckListener = new AppExistanceCheckListener<CheckTelegramExistanceProcessor>(CheckTelegramExistanceProcessor.class);
		mqListeners.add(telegramCheckListener);
		registrationConsumer.setMessageListener(telegramCheckListener);
		
		// Запускаем Listener
		logger.info("Initializing check application existance in Viber consumer for queue [{}]", QueueNameConstants.MQ_CHECK_VIBER_QUEUE);
		MessageConsumer viberCheckConsumer = session.createConsumer(session.createQueue(QueueNameConstants.MQ_CHECK_VIBER_QUEUE));
		mqMessageConsumers.add(viberCheckConsumer);
		AppExistanceCheckListener<CheckViberExistanceProcessor> viberCheckListener = new AppExistanceCheckListener<CheckViberExistanceProcessor>(CheckViberExistanceProcessor.class);
		mqListeners.add(viberCheckListener);
		registrationConsumer.setMessageListener(viberCheckListener);
		
		// Запускаем Listener
		logger.info("Initializing check application existance in Whatsapp consumer for queue [{}]", QueueNameConstants.MQ_CHECK_WHATSAPP_QUEUE);
		MessageConsumer whatsappCheckConsumer = session.createConsumer(session.createQueue(QueueNameConstants.MQ_CHECK_WHATSAPP_QUEUE));
		mqMessageConsumers.add(whatsappCheckConsumer);
		AppExistanceCheckListener<CheckWhatsAppExistanceProcessor> whatsAppCheckListener = new AppExistanceCheckListener<CheckWhatsAppExistanceProcessor>(CheckWhatsAppExistanceProcessor.class);
		mqListeners.add(whatsAppCheckListener);
		registrationConsumer.setMessageListener(whatsAppCheckListener);
		*/
		
		registerQueueConsumer(session, QueueNameConstants.MQ_REGISTRATION_QUEUE, new RegistrationPhoneQueueListener());
		registerQueueConsumer(session, QueueNameConstants.MQ_CHECK_TELEGRAM_QUEUE, new AppExistanceCheckListener<CheckTelegramExistanceProcessor>(CheckTelegramExistanceProcessor.class));
		registerQueueConsumer(session, QueueNameConstants.MQ_CHECK_VIBER_QUEUE, new AppExistanceCheckListener<CheckViberExistanceProcessor>(CheckViberExistanceProcessor.class));
		registerQueueConsumer(session, QueueNameConstants.MQ_CHECK_WHATSAPP_QUEUE, new AppExistanceCheckListener<CheckWhatsAppExistanceProcessor>(CheckWhatsAppExistanceProcessor.class));
		
	}
	
	private static void registerQueueConsumer(Session session, String queueName, MessageListener listener) throws JMSException {
		logger.info("Initializing registration consumer for queue [{}]", queueName);
		MessageConsumer registrationConsumer = session.createConsumer(session.createQueue(queueName));
		mqMessageConsumers.add(registrationConsumer);
		mqListeners.add(listener);
		registrationConsumer.setMessageListener(listener);
	}
	
}
