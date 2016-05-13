package kz.aphion.adverts.phone;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import kz.aphion.adverts.phone.listener.RegistrationPhoneQueueListener;
import kz.aphion.adverts.phone.providers.ActiveMqProvider;

/**
 * Класс инициализатор Listeners на очереди MQ сообщений
 * @author artem.demidovich
 *
 */
public class MqConsumerInitializator {
	
	
	public static final String MQ_REGISTRATION_QUEUE = "adverts.phones.registration";
	
	public static List<MessageConsumer> mqMessageConsumers = new ArrayList<MessageConsumer>();
	public static List<MessageListener> mqListeners = new ArrayList<MessageListener>();
	
	public static void initListeners() throws JMSException, Exception {
		Session session =  ActiveMqProvider.getInstance().getSession();
		
		// Запускаем Listener по регистрации телефонов
		MessageConsumer registrationConsumer = session.createConsumer(session.createQueue(MQ_REGISTRATION_QUEUE));
		mqMessageConsumers.add(registrationConsumer);
		RegistrationPhoneQueueListener registrationListener = new RegistrationPhoneQueueListener();
		mqListeners.add(registrationListener);
		registrationConsumer.setMessageListener(registrationListener);
		registrationConsumer.receive(5000);
		
		
	}
	
	
}
