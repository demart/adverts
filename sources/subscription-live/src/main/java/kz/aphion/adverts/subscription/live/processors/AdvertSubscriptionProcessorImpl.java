package kz.aphion.adverts.subscription.live.processors;

import javax.jms.JMSException;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.common.models.mq.adverts.AnalyserProcessModel;
import kz.aphion.adverts.persistence.adverts.Advert;
import kz.aphion.adverts.subscription.live.ConnectionManager;
import kz.aphion.adverts.subscription.live.utils.MessageUtils;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Класс обработчик сообщений о недвижимости
 * 
 * @author artem.demidovich
 *
 * Created at Jun 12, 2016
 */
public class AdvertSubscriptionProcessorImpl implements AdvertSubscriptionProcessor {
	
	private static Logger logger = LoggerFactory.getLogger(AdvertSubscriptionProcessorImpl.class);
	
	/**
	 * Метод является входной точкой для обработки запроса
	 * @param message
	 * @throws Exception 
	 * @throws JMSException 
	 */
	public void processMessage(String message) throws JMSException, Exception {
		AnalyserProcessModel model = MessageUtils.parseModel(message);
		if (model == null) {
			// Не смогли извлечь, ругаемся и приступаем к следующему сообщению
			logger.warn("Can't process message [" + message + "] Message does not belong to AdvertAnalyserToSubscriptionProcessModel");
			return;
		}
		
		//logger.info("New message: {}", message);

		Datastore ds = DB.DS();
		
		Advert advert = ds.get(Advert.class, new ObjectId(model.advertId));
		if (advert == null) {
			logger.warn("Received Advert.id {} which does not exist in db, processing skipped", model.advertId);
			return;
		}
		
		ConnectionManager.notifyClient(advert);
		
		// 1. Просматриваем список всех live подписок на сервере
		// 2. Проходимся по каждой подписке
		// 3. 	Обновляем кол-во объявлени
		// 4.	Добавляем рекламные если нужно
		// 5. 	Если не платный пользователь то проверяем лимит
		// 6.	Отправляем данные пользователю
		// 7.	Обновляем статистические данные в монго
		// 8.
		
	}
	
}
