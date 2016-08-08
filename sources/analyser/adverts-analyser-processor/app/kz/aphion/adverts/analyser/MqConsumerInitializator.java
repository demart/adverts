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
				
		// Запускаем Listener по обработке объявлений о недвижимости
		Logger.info("Initializing registration consumer for queue [%s]", QueueNameConstants.MQ_REALTY_ADVERTS_QUEUE);
		MessageConsumer registrationConsumer = session.createConsumer(session.createQueue(QueueNameConstants.MQ_REALTY_ADVERTS_QUEUE));
		mqMessageConsumers.add(registrationConsumer);
		RealtyAdvertListener realtyAdvertListener = new RealtyAdvertListener();
		mqListeners.add(realtyAdvertListener);
		registrationConsumer.setMessageListener(realtyAdvertListener);

//		// Запускаем Listener
//		Logger.info("Initializing check application existance on Telegram consumer for queue [%s]", QueueNameConstants.MQ_CHECK_TELEGRAM_QUEUE);
//		MessageConsumer telegramCheckConsumer = session.createConsumer(session.createQueue(QueueNameConstants.MQ_CHECK_TELEGRAM_QUEUE));
//		mqMessageConsumers.add(telegramCheckConsumer);
//		AppExistanceCheckListener<CheckTelegramExistanceProcessor> telegramCheckListener = new AppExistanceCheckListener<CheckTelegramExistanceProcessor>(CheckTelegramExistanceProcessor.class);
//		mqListeners.add(telegramCheckListener);
//		registrationConsumer.setMessageListener(telegramCheckListener);
//		
//		// Запускаем Listener
//		Logger.info("Initializing check application existance in Viber consumer for queue [%s]", QueueNameConstants.MQ_CHECK_VIBER_QUEUE);
//		MessageConsumer viberCheckConsumer = session.createConsumer(session.createQueue(QueueNameConstants.MQ_CHECK_VIBER_QUEUE));
//		mqMessageConsumers.add(viberCheckConsumer);
//		AppExistanceCheckListener<CheckViberExistanceProcessor> viberCheckListener = new AppExistanceCheckListener<CheckViberExistanceProcessor>(CheckViberExistanceProcessor.class);
//		mqListeners.add(viberCheckListener);
//		registrationConsumer.setMessageListener(viberCheckListener);
//		
//		// Запускаем Listener
//		Logger.info("Initializing check application existance in Whatsapp consumer for queue [%s]", QueueNameConstants.MQ_CHECK_WHATSAPP_QUEUE);
//		MessageConsumer whatsappCheckConsumer = session.createConsumer(session.createQueue(QueueNameConstants.MQ_CHECK_WHATSAPP_QUEUE));
//		mqMessageConsumers.add(whatsappCheckConsumer);
//		AppExistanceCheckListener<CheckWhatsAppExistanceProcessor> whatsAppCheckListener = new AppExistanceCheckListener<CheckWhatsAppExistanceProcessor>(CheckWhatsAppExistanceProcessor.class);
//		mqListeners.add(whatsAppCheckListener);
//		registrationConsumer.setMessageListener(whatsAppCheckListener);
		
	}
	
	
}
