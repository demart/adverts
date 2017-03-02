package kz.aphion.adverts.subscription.processors;

import javax.jms.JMSException;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.subscription.mq.SubscriptionNotificationBuilderModel;

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
		
		
		Datastore ds = DB.DS();
		
		// Получаем сообщение
		// Проверяем не исчек ли срок жизни, так как мало ли что у нас могли где застрять
		// Получаем данные из БД
		// Проверяем наличие данных и статус (например чувак решил отключить подписку между нашими двумя модулями)
		// 
		
		// Алгоритм должен работать следующим образом
		// 1. Сюда попадают только те подписки у которых есть какие либо объявление о которых нужно знать пользователю
		// 2. Поэтому чтобы поддерживать модуль простым, мы предполагем что объявления были сформированы до этого и их нужно просто обработать
		// 3. Проверяем действительность подписки
		// 	3.1. Игнорируем если подписка не активна
		//	3.2. Игнорируем если подписка платная и уже не дельствительна
		//	3.3. Игнорируем в случае с проблемой записи в базе 
		// 4. Получаем список каналов для уведомлений для указанной подписки
		// 5. Отправляем запрос на генерацию рекламного контента для указанных каналов (и юзера)
		// 6. Шаблонирируем сообщение с объявлениями и рекламным контентом
		// 7. Наполняем запрос параметрами и отправляем в очередь уведомлений
		// 8. Думаем нужно ли нам получать фидбэк от уведомлений
		
		// ПОДУМАТЬ: Кто должен подставить ссылки с URL REDIRECT + STATISTICS
		// ПОДУМАТЬ: Как считать переходы, выгрузку контента и публикацию самого конетнта
		
		
		
		
		
	}

}
