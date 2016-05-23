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
import play.Logger;

/**
 * Класс инициализатор Listeners на очереди MQ сообщений
 * @author artem.demidovich
 *
 */
public class MqConsumerInitializator {
	
	public static List<MessageConsumer> mqMessageConsumers = new ArrayList<MessageConsumer>();
	public static List<MessageListener> mqListeners = new ArrayList<MessageListener>();
	
	public static void initListeners() throws JMSException, Exception {
		Session session =  ActiveMqProvider.getInstance().getSession();
		
		// Запускаем Listener по регистрации телефонов
		Logger.info("Initializing registration consumer for queue [%s]", QueueNameConstants.MQ_REGISTRATION_QUEUE);
		MessageConsumer registrationConsumer = session.createConsumer(session.createQueue(QueueNameConstants.MQ_REGISTRATION_QUEUE));
		mqMessageConsumers.add(registrationConsumer);
		RegistrationPhoneQueueListener registrationListener = new RegistrationPhoneQueueListener();
		mqListeners.add(registrationListener);
		registrationConsumer.setMessageListener(registrationListener);
		
		// Запускаем Listener
		Logger.info("Initializing check application existance on Telegram consumer for queue [%s]", QueueNameConstants.MQ_CHECK_TELEGRAM_QUEUE);
		MessageConsumer telegramCheckConsumer = session.createConsumer(session.createQueue(QueueNameConstants.MQ_CHECK_TELEGRAM_QUEUE));
		mqMessageConsumers.add(telegramCheckConsumer);
		AppExistanceCheckListener<CheckTelegramExistanceProcessor> telegramCheckListener = new AppExistanceCheckListener<CheckTelegramExistanceProcessor>(CheckTelegramExistanceProcessor.class);
		mqListeners.add(telegramCheckListener);
		registrationConsumer.setMessageListener(telegramCheckListener);
		
		// Запускаем Listener
		Logger.info("Initializing check application existance in Viber consumer for queue [%s]", QueueNameConstants.MQ_CHECK_VIBER_QUEUE);
		MessageConsumer viberCheckConsumer = session.createConsumer(session.createQueue(QueueNameConstants.MQ_CHECK_VIBER_QUEUE));
		mqMessageConsumers.add(viberCheckConsumer);
		AppExistanceCheckListener<CheckViberExistanceProcessor> viberCheckListener = new AppExistanceCheckListener<CheckViberExistanceProcessor>(CheckViberExistanceProcessor.class);
		mqListeners.add(viberCheckListener);
		registrationConsumer.setMessageListener(viberCheckListener);
		
		// Запускаем Listener
		Logger.info("Initializing check application existance in Whatsapp consumer for queue [%s]", QueueNameConstants.MQ_CHECK_WHATSAPP_QUEUE);
		MessageConsumer whatsappCheckConsumer = session.createConsumer(session.createQueue(QueueNameConstants.MQ_CHECK_WHATSAPP_QUEUE));
		mqMessageConsumers.add(whatsappCheckConsumer);
		AppExistanceCheckListener<CheckWhatsAppExistanceProcessor> whatsAppCheckListener = new AppExistanceCheckListener<CheckWhatsAppExistanceProcessor>(CheckWhatsAppExistanceProcessor.class);
		mqListeners.add(whatsAppCheckListener);
		registrationConsumer.setMessageListener(whatsAppCheckListener);
		
	}
	
	
}
