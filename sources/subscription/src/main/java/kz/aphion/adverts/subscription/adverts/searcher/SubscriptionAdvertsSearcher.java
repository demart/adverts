package kz.aphion.adverts.subscription.adverts.searcher;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.common.MQ;
import kz.aphion.adverts.persistence.subscription.Subscription;
import kz.aphion.adverts.persistence.subscription.SubscriptionAdvert;
import kz.aphion.adverts.common.mq.QueueNameConstants;
import kz.aphion.adverts.subscription.mq.SubscriptionNotificationBuilderModel;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс занимается поиском всех объявлений о котороых необходимо сообщить пользователю
 * 
 * @author artem.demidovich
 *
 * Created at Aug 18, 2016
 */
public class SubscriptionAdvertsSearcher {

	private static Logger logger = LoggerFactory.getLogger(SubscriptionAdvertsSearcher.class);
	
	
	public void search() throws Exception {
		logger.debug("Starts searching for adverts in subscriptions...");
		Datastore ds = DB.DS();
		
		// Строим запрос
		Query<Subscription> query = SubscriptionAdvertsQueryBuilder.buildSearchQuery(ds);
		
		// Выполняем поиск
		List<Subscription> subscriptions = query.asList();
		
		// Проходим по каждой подписке
		//	Выбираем только не отправленные (дополнительный подзапрос)
		//	Обновляем данные по подписке (сбрасываем счетчики и время следующего запуска)
		//	Формируем запрос и отправляем в очередь
		// Завершаемся
		processSubscriptions(ds, subscriptions);

		
		logger.debug("Finished searching for adverts in subscriptions. Processed {} subscriptions", subscriptions.size());
	}
	
	
	private void processSubscriptions(Datastore ds, List<Subscription> subscriptions) {
		if (subscriptions == null || subscriptions.size() < 1) {
			logger.debug("There is no any subscription to process...");
			return;
		}
		
		// Проходим по каждой подписке
		//	Выбираем только не отправленные
		//	Обновляем данные по подписке (сбрасываем счетчики и время следующего запуска)
		//	Формируем запрос и отправляем в очередь
		// Завершаемся
		for (Subscription subscription : subscriptions) {
			try {
				processSubscription(ds, subscription);
			} catch (Exception ex) {
				logger.error("Error during processing subscription " + subscription.id, ex);
			}
		}
		
	}
	
	
	private void processSubscription(Datastore ds, Subscription subscription) {
		if (subscription == null)
			return;
		
		logger.debug("Processing subscription {}", subscription.id);
		
		// Выполняем запрос в бд 
		List<SubscriptionAdvert> adverts = getNeededAdverts(ds, subscription);
		if (adverts == null || adverts.size() < 1) {
			logger.debug("Subscription {} doesn't contain any adverts to notify", subscription.id);
		}
		
		// Сбрасываем счетчики подписки и продолжаем обрабтку
		// Даже если что-то пойдет не так, мы уже успешно сбросили счетчики и
		// конечный пользователь не получить повторно тоже самое в кототкий промежуток времени
		resetSubscriptionCounters(ds, subscription);
		
		// Проверяем если нету никаких объявлений, то завершаем процесс обработки данной подписки
		if (adverts == null || adverts.size() < 1) {
			logger.debug("Subscription {} doesn't contain any adverts to notify, subscriptin will be skipped.", subscription.id);
			return;
		}
		
		SubscriptionNotificationBuilderModel model = new SubscriptionNotificationBuilderModel();
		model.subscriptionId = subscription.id.toHexString();
		model.subscriptionAdvertIds = new ArrayList<String>();
		
		// Подгаввливаем сообщение для отправки в очередь
		for (SubscriptionAdvert subscriptionAdvert : adverts) {
			
			// Помечаем сообщение в базе как уже обрабтанное
			markSubscriptionAdvertAsSent(ds, subscriptionAdvert);
			
			// добавляем в список, так как потом будем обрабатывать
			model.subscriptionAdvertIds.add(subscriptionAdvert.id.toHexString());
		}
		
		// указываем текущее время
		model.eventTime = Calendar.getInstance();
		
		try {
			// Строим объект который необходимо положить в очередь
			String message = model.toJSON();
			
			// Отправяем сообщение в очередь
			MQ.INSTANCE.sendTextMessageToQueue(QueueNameConstants.ADVERTS_SUBSCRIPTION_NOTIFICATION_BUILDER_QUEUE.getValue(), message);
			
		} catch (Exception e) {
			logger.error("Error whily trying to send message to subscription notification builder", e);
			
			// TODO Подумать может быть здесь откатиться с отметками advert типа не отправлено
			// чтобы система когда будет в следующий раз рассылать также включала это объявление???
			
		}
		
	}
	
	
	private void markSubscriptionAdvertAsSent(Datastore ds, SubscriptionAdvert advert) {
		// Помечаем объявление как обработанное
		advert.wasNotificationSent = true;
		advert.notificationWasSentAt = Calendar.getInstance();
		advert.updated = Calendar.getInstance();
		ds.merge(advert);
	}
	
	private void resetSubscriptionCounters(Datastore ds, Subscription subscription) {
		// Сбрасываем все криетерии для поиска
		// Основная задача чтобы при следующем проходе опять не попалась эта подписка
		subscription.notification.lastNotifiedTime = Calendar.getInstance();
		subscription.notification.isMinimumAmountAchieved = false;
		subscription.notification.newAdvertsCount = 0;
		subscription.notification.nextSchedulerScanTime = getNextSchedulerScanTime(subscription);

		// сохраняем изменения
		ds.merge(subscription);
		logger.debug("Reset all run counters in subscription {} ", subscription.id);
	}
	
	
	private Calendar getNextSchedulerScanTime(Subscription subscription) {
		Calendar nextStartTime = Calendar.getInstance();
		nextStartTime.add(Calendar.MINUTE, subscription.notification.scheduledRunEvery);
		return nextStartTime;
	}

	/**
	 * Пробегается по списку объявлений в подписке и если необходимо уведомить то добавляет
	 * в список и возвращает обратно
	 * @param ds подключение к монго
	 * @param subscription подписка
	 * @return список объвялений о которых нужно сообщить пользователю
	 */
	private List<SubscriptionAdvert> getNeededAdverts(Datastore ds, Subscription subscription) {
		// Подготавливаем запрос для поиска объявлений
		Query<SubscriptionAdvert> query = SubscriptionAdvertsQueryBuilder.buildSubscriptionAdvertsQuery(ds, subscription);
		
		// Выполняем уведомления
		List<SubscriptionAdvert> result = query.asList();

		logger.debug("Subscription: {} has {} adverts to notify.", subscription.id, result.size());
		
		return result;
	}
	
}
