package kz.aphion.adverts.subscription.processors;

import javax.jms.JMSException;

import kz.aphion.adverts.subscription.mq.SubscriptionNotificationBuilderModel;
import kz.aphion.adverts.subscription.providers.MongoDbProvider;

import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс отвечает за обработку запросов поступающих из очередь для 
 * мнгновенных уведомлений
 * @author artem.demidovich
 *
 * Created at Aug 18, 2016
 */

public class SubscriptionAdvertNotificaitonBuilderProcessor implements AdvertSubscriptionProcessor {

	private static Logger logger = LoggerFactory.getLogger(SubscriptionAdvertNotificaitonBuilderProcessor.class);
	
	@Override
	public void processMessage(String message) throws JMSException, Exception {
		SubscriptionNotificationBuilderModel model = SubscriptionNotificationBuilderModel.parseModel(message);
		if (model == null) {
			// Не смогли извлечь, ругаемся и приступаем к следующему сообщению
			logger.warn("Can't process message [" + message + "] Message does not belong to " +  SubscriptionNotificationBuilderModel.class.getName());
			return;
		}
		
		
		Datastore ds = MongoDbProvider.getInstance().getDatastore();
		
		// Получаем сообщение
		// Проверяем не исчек ли срок жизни, так как мало ли что у нас могли где застрять
		// Получаем данные из БД
		//	Проверяем наличие данных и статус (например чувак решил отключить подписку между нашими двумя модулями)
		//			
	}

}
