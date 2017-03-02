package kz.aphion.adverts.subscription.processors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.jms.JMSException;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.common.MQ;
import kz.aphion.adverts.common.mq.QueueNameConstants;
import kz.aphion.adverts.persistence.subscription.Subscription;
import kz.aphion.adverts.persistence.subscription.SubscriptionAdvert;
import kz.aphion.adverts.persistence.subscription.SubscriptionAdvertStatus;
import kz.aphion.adverts.persistence.subscription.notification.SubscriptionNotificationType;
import kz.aphion.adverts.subscription.mq.RealtyAnalyserToSubscriptionProcessModel;
import kz.aphion.adverts.subscription.mq.SubscriptionNotificationBuilderModel;
import kz.aphion.adverts.subscription.mq.SubscriptionProcessStatus;
import kz.aphion.adverts.subscription.searcher.SubscriptionSearcher;
import kz.aphion.adverts.subscription.searcher.SubscriptionSearcherFactory;
import kz.aphion.adverts.subscription.utils.MessageUtils;

import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
		RealtyAnalyserToSubscriptionProcessModel model = MessageUtils.parseModel(message);
		if (model == null) {
			// Не смогли извлечь, ругаемся и приступаем к следующему сообщению
			logger.warn("Can't process message [" + message + "] Message does not belong to RealtyAnalyserToSubscriptionProcessModel");
			return;
		}

		// Типовой процесс
		// 1. Если объявление новое
		//	 	-> Необходимо найти все активные подписки
		//			Возможно дополнительно проверить лимимы и другие вещи связанные с подписками пользователя
		//		-> проходим по каждой подписке
		//			-> добавляем объявление в верх (или потом отсортируем)
		//			-> помечаем как не прочитанное
		//			-> помечаем как не отправленное для уведомления		
		//			-> читаем настройки уведомлений
		//			-> если включен режим немедленного уведолмения отправляем в очередь
		//			-> 
		//
		// 2. Если объялание улучшилось
		//		-> Необходимо найти все активные подписки
		//			Возможно дополнительно проверить лимимы и другие вещи связанные с подписками пользователя
		//		-> проходим по каждой подписке
		//			-> добавляем объявление в верх (или потом отсортируем)
		//			-> помечаем как улучшенное		
		//			-> помечаем как не прочитанное
		//			-> помечаем как не отправленное для уведомления	
		//			-> читаем настройки уведомлений		
		//			-> если включен режим немедленного уведолмения отправляем в очередь		
		//		-> Находим старое объявление и помечаем как замененное (хотя это можено сделать потом)
		//
		// 3. Если объялание ухудшилось		
		//		-> Необходимо найти все активные подписки
		//		Возможно дополнительно проверить лимимы и другие вещи связанные с подписками пользователя
		//		-> проходим по каждой подписке
		//			-> добавляем объявление в верх (или потом отсортируем)
		//			-> помечаем как ухудщенное		
		//			-> помечаем как не прочитанное
		//			-> помечаем как не отправленное для уведомления	
		//			-> читаем настройки уведомлений		
		//			-> если включен режим немедленного уведолмения отправляем в очередь	
		//		-> Находим старое объявление и помечаем как замененное (хотя это можено сделать потом)
		
		switch(model.status) {
			case SAME:
				logger.error("SAME advert comes to subscription module, should be checked and implemented.");
				// SubscriptionProcessStatus.NEW ???
				processAdvert(model, SubscriptionAdvertStatus.NORMAL, SubscriptionProcessStatus.NEW);
				break;
			case NEW:
				processAdvert(model, SubscriptionAdvertStatus.NEW, SubscriptionProcessStatus.NEW);
				break;
			case BETTER:
				processAdvert(model, SubscriptionAdvertStatus.BETTER, SubscriptionProcessStatus.BETTER);
				break;
			case WORSTE:
				processAdvert(model, SubscriptionAdvertStatus.WORSTE, SubscriptionProcessStatus.WORSTE);
				break;
			default:
				logger.warn("Found unknown status " + model.status + " processing will be skipped.");
		}
	}

	
	private void processAdvert(RealtyAnalyserToSubscriptionProcessModel model, SubscriptionAdvertStatus targetAdvertStatus, SubscriptionProcessStatus targetProcessStatus) throws JMSException, Exception {
		
		Datastore ds = DB.DS();
		
		// Получаем искателя подписок под соответсвующий тип
		SubscriptionSearcher searcher = SubscriptionSearcherFactory.getRealtySubscriptionSearcher(model);
			
		// Выполняем поиск подписок
		List<Subscription> subscriptions = searcher.search();
		
		if (subscriptions == null) {
			logger.debug("Subscriptions not found for advertId {} ...", model.advertId);
		} else {
			for (Subscription subscription : subscriptions) {
				logger.debug("Processing subscription with id: {}", subscription.id);
				
				//Статус объявления в подписке
				SubscriptionAdvertStatus advertStatus = SubscriptionAdvertStatus.NEW;
				
				// Статус объявления при отправке в систему уведомлений
				SubscriptionProcessStatus processStatus = SubscriptionProcessStatus.NEW;
				
				List<SubscriptionAdvert> adverts = subscription.adverts;
				if (adverts == null) {
					// Если не было до этого объявлений в подписке
					adverts = new ArrayList<SubscriptionAdvert>();
					subscription.adverts = adverts;
				} else {
					// Если объявления были то нужно найти и заменить старое если оно есть
					logger.debug("Subscription has adverts, trying to find and mark as REPLACED");
					if (findAndReplaceExistingAdverts(ds, adverts, model.oldAdvertId, model.advertId)) {
						advertStatus = targetAdvertStatus;
						processStatus = targetProcessStatus;
					}
				}

				SubscriptionAdvert advert = createSubscriptionAdvertAndAddToSubscription(subscription, searcher, advertStatus);

				// Обновляем данные
				ds.save(advert);
				ds.merge(subscription);

				// Отправляем только если успешно всё обработали и сохранили
				sendImmediateNotificationMessageIfRequired(subscription, advert, processStatus);

				logger.debug("Subscriptions id [" + subscription.id + "] successfully merged with new advert result object");

			}
		}		
		
		logger.debug("Realty model with id: "+ model.advertId + " was successfully processed.");
	}
	
	/**
	 * Метод проверяет есть ли в подписке старые или новые объявления,
	 * которые необходимо пометить как замененные REPLACED
	 * @param ds
	 * @param adverts
	 * @param oldAdvertId
	 * @param advertId
	 * @return true елси были найдены и заменены старые объекты
	 */
	private boolean findAndReplaceExistingAdverts(Datastore ds, List<SubscriptionAdvert> adverts, String oldAdvertId, String advertId) {
		boolean foundExistingAdvert = false;

		for (SubscriptionAdvert subscriptionAdvert : adverts) {
			if (subscriptionAdvert.advert.id.toString().equals(oldAdvertId)) {

				// Если есть в подписке старое объявление, то его нужно заменить новым
				subscriptionAdvert.status = SubscriptionAdvertStatus.REPLACED;
				subscriptionAdvert.updated = Calendar.getInstance();
				ds.merge(subscriptionAdvert);

				foundExistingAdvert = true;

				logger.debug("Found SubscriptionAdvert with id: {} and marked as REPLACED", subscriptionAdvert.id);
			}

			if (subscriptionAdvert.advert.id.toString().equals(advertId)) {

				// Если по каким то причинам объявление с таким же ID уже есть в подписке
				// то это форс мажор, и необходимо его пометить и поругаться в логах
				subscriptionAdvert.status = SubscriptionAdvertStatus.REPLACED;
				subscriptionAdvert.updated = Calendar.getInstance();
				ds.merge(subscriptionAdvert);

				foundExistingAdvert = true;

				logger.warn("Found DUPLICATE SubscriptionAdvert with id: {} and marked as REPLACED. Please check why it happened.", subscriptionAdvert.id);
			}
		}

		return foundExistingAdvert;
	}
	
	
	/**
	 * Создает запись объявления в подписке
	 * @param subscription
	 * @param searcher
	 * @param status
	 * @return
	 */
	private SubscriptionAdvert createSubscriptionAdvertAndAddToSubscription(Subscription subscription, SubscriptionSearcher searcher, SubscriptionAdvertStatus status) {
		SubscriptionAdvert advert = new SubscriptionAdvert();
		
		advert.subscription = subscription;
		advert.advert = searcher.getAdvertObject();
		advert.created = Calendar.getInstance();
		advert.updated = Calendar.getInstance();
		advert.viewedAt = null;
		advert.hasBeenViewed = false;
		advert.wasNotificationSent = false;
		advert.notificationWasSentAt = null;
		advert.status = status;
		
		subscription.adverts.add(advert);
		
		return advert;
	}
	
	/**
	 * Отправляет сообщение в очередь немедленных уведомлений.
	 * Метод должен использоваться только после сохранения данных, так как нужна гарантия
	 * а также id записей в БД
	 * @param subscription
	 * @param advert
	 * @param status Пока не используется, так как не понятно как это будет сделано...
	 * @throws Exception Ошибки при отправке сообщения
	 * @throws JMSException ошибки при отправке сообщения
	 */
	private void sendImmediateNotificationMessageIfRequired(Subscription subscription, SubscriptionAdvert advert, SubscriptionProcessStatus status) throws JMSException, Exception {
		if (subscription.notification != null) {
			if (SubscriptionNotificationType.IMMEDIATE.equals(subscription.notification.type)) {
		
				logger.debug("Building message to notification system with subscriptionAdvertId: {}", advert.id);
				SubscriptionNotificationBuilderModel model = new SubscriptionNotificationBuilderModel();

				model.subscriptionId = subscription.id.toString();
				model.subscriptionAdvertIds = Arrays.asList(advert.id.toString());
				model.eventTime = Calendar.getInstance();
				//model.status = status; // прочитаем его из базы
				
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String message = gson.toJson(model);
				
				MQ.INSTANCE.sendTextMessageToQueue(QueueNameConstants.ADVERTS_SUBSCRIPTION_NOTIFICATION_BUILDER_QUEUE.getValue(), message);
				logger.debug("Message with immediate status was sent to notification system.");
				
			}
		}
	}
	
	
}
