package kz.aphion.adverts.subscription.live.processors;

import javax.jms.JMSException;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.realty.data.flat.FlatRentRealty;
import kz.aphion.adverts.persistence.realty.data.flat.FlatSellRealty;
import kz.aphion.adverts.subscription.live.ConnectionManager;
import kz.aphion.adverts.subscription.live.mq.RealtyAnalyserToSubscriptionProcessModel;

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
public class RealtyAdvertSubscriptionProcessor implements AdvertSubscriptionProcessor {
	
	private static Logger logger = LoggerFactory.getLogger(RealtyAdvertSubscriptionProcessor.class);
	
	/**
	 * Метод является входной точкой для обработки запроса
	 * @param message
	 * @throws Exception 
	 * @throws JMSException 
	 */
	public void processMessage(String message) throws JMSException, Exception {
		RealtyAnalyserToSubscriptionProcessModel model = RealtyAnalyserToSubscriptionProcessModel.parseModel(message);
		if (model == null) {
			// Не смогли извлечь, ругаемся и приступаем к следующему сообщению
			logger.warn("Can't process message [" + message + "] Message does not belong to RealtyAnalyserToSubscriptionProcessModel");
			return;
		}
		
		//logger.info("New message: {}", message);

		Datastore ds = DB.DS();
		
		BaseEntity advert = null;
				
		switch (model.type) {
		case FLAT:
			switch (model.operation) {
			case SELL:
				advert = ds.get(FlatSellRealty.class, new ObjectId(model.advertId));
				break;
				
			case RENT:
				advert = ds.get(FlatRentRealty.class, new ObjectId(model.advertId));
				break;
				
			default:
				break;
			}
			break;

		default:
			break;
		}
		
		if (advert == null) {
			logger.warn("Object {} with type {} and operation {} not found", model.advertId, model.type, model.operation);
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
