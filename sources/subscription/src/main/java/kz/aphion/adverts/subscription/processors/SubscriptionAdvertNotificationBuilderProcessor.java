package kz.aphion.adverts.subscription.processors;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.jms.JMSException;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.common.MQ;
import kz.aphion.adverts.common.mq.QueueNameConstants;
import kz.aphion.adverts.notification.mq.models.NotificationChannelType;
import kz.aphion.adverts.notification.mq.models.NotificationEventMessage;
import kz.aphion.adverts.persistence.subscription.Subscription;
import kz.aphion.adverts.persistence.subscription.SubscriptionAdvert;
import kz.aphion.adverts.persistence.subscription.SubscriptionAdvertStatus;
import kz.aphion.adverts.persistence.subscription.SubscriptionStatus;
import kz.aphion.adverts.persistence.subscription.notification.SubscriptionNotificationChannelType;
import kz.aphion.adverts.persistence.users.UserDevice;
import kz.aphion.adverts.persistence.users.UserDeviceStatus;
import kz.aphion.adverts.subscription.builder.notification.NotificationEventBuilder;
import kz.aphion.adverts.subscription.mq.SubscriptionNotificationBuilderModel;

import org.bson.types.ObjectId;
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

public class SubscriptionAdvertNotificationBuilderProcessor implements AdvertSubscriptionProcessor {

	private static Logger logger = LoggerFactory.getLogger(SubscriptionAdvertNotificationBuilderProcessor.class);
	
	@Override
	public void processMessage(String message) throws JMSException, Exception {
		SubscriptionNotificationBuilderModel model = SubscriptionNotificationBuilderModel.parseModel(message);
		if (model == null) {
			// Не смогли извлечь, ругаемся и приступаем к следующему сообщению
			logger.warn("Can't process message [" + message + "] Message does not belong to " +  SubscriptionNotificationBuilderModel.class.getName());
			return;
		}
		
		// Check model for important params
		try {
			model.check();
		} catch (Exception ex) {
			logger.warn("Model is incorrect: {}", ex.getMessage());
			return;
		}
		
		Datastore ds = DB.DS();
		
		Subscription subscription = getActiveSubscription(ds, model);
		if (subscription == null) {
			// Подписка не найдена завершаем процесс
			logger.warn("Active subscription with id {} not found, message will be skipped", model.subscriptionId);
			return;
		}
		
		if (subscription.notification == null) {
			// Объявлений для уведомлений не найдено
			logger.error("Active Subscription id {} has null notification object, message will be skipped", model.subscriptionId);
			return;
		}
		

		List<SubscriptionAdvert> subscriptionAdverts = getActiveSubscriptionAdverts(ds, model, subscription);
		if (subscriptionAdverts == null || subscriptionAdverts.size() < 1) {
			// Объявлений для уведомлений не найдено
			logger.warn("Active adverts to notify in subscption with id {} not found, message will be skipped", model.subscriptionId);
			return;
		}
		

		// Определяем каналы и отправляем шаблонизированные уведомления
		if (buildAndSendNotificationMessage(ds, subscription, subscriptionAdverts) == false) {
			logger.warn("There were some issues during generation notification with subscription.id {}, message will be skipped", model.subscriptionId);
			return;
		}
		
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

	
	/**
	 * Метод формирует список каналов для уведомения, шаблонизириует уведомление для каждого канала
	 * и отправляет одно единственное уведомение в систему уведомлений.
	 * WARN: Колбэки пока не используеются
	 * @param ds
	 * @param subscription
	 * @param subscriptionAdverts
	 * @throws JMSException 
	 */
	private boolean buildAndSendNotificationMessage(Datastore ds, Subscription subscription, List<SubscriptionAdvert> subscriptionAdverts) throws JMSException {

		// 1. Взять все каналы
		// 2. Получить маркетинговый контент (желательно сразу по всех каналам)
		// 2. Отшаблонизировать сообщения для каналов
		// 3. Отправить уведомление
		
		// Модуль маркетинга (лучше передавать параметрами = списком)
		// 1. Тип события
		// 2. Пользователь
		// 3. Каналы
		// 4. SubscriptionType
		// 5. 
		// 6. и д.р.
		
		// Получаем список каналов для уведомления
		List<SubscriptionNotificationChannelType> channels = getRequiredNotificationChannels(subscription);

		if (channels == null || channels.size() < 1) {
			logger.warn("In subscription Id {}, no any channels to notify...", subscription.id);
			return false;
		}
		
		
		// Вызвать маркетинговый модуль для получения рекламного контента
		// TODO Вызвать маркетинговый модуль
		
		// Шаблонизация уведомления под выбранный канал
		NotificationEventBuilder notificationBuilder = new NotificationEventBuilder();
		
		for (SubscriptionNotificationChannelType channelType : channels) {
			
			switch (channelType) {
				case MOBILE:
					addMobileChannelsToNotification(subscription,subscriptionAdverts, notificationBuilder);
					break;
					
				case WEB:
					addWebChannelsToNotification(subscription,subscriptionAdverts, notificationBuilder);
					break;

				case EMAIL:
					// Добавляем емаил уведомление
					notificationBuilder.addChannel(NotificationChannelType.EMAIL, subscription, subscriptionAdverts);
					break;
					
				default:
					logger.warn("Undefined channelType {}, please check sources what is the problem??", channelType);
					break;
			}
			
			
			// Засунуть канал в билдер
			// TODO Add Marketing Data
			
			// TODO Add DoNotDisturb!
			
			// Что делать с SMS??? НУЖЕН НЕ НУЖЕН?
			// Что делать с MACOS??? НУЖЕН НЕ НУЖЕН? Чет как-то не понятно 
			// получается из-за того что разные каналы в подписке и возможные каналы в уведомлении			
			
		}
		
		// Формируем собщение
		NotificationEventMessage message = notificationBuilder.build();

		// Отправляем уведомление для рассылки
		MQ.INSTANCE.sendTextMessageToQueue(QueueNameConstants.NOTIFICATION_QUEUE.getValue(), message.toJson());
		
		
		return true;
	}
	
	
	private boolean addMobileChannelsToNotification(Subscription subscription, List<SubscriptionAdvert> subscriptionAdverts, NotificationEventBuilder notificationBuilder) {
		boolean wasMobileFound = false;
		for (UserDevice userDevice : subscription.user.devices) {
			if (userDevice.status == UserDeviceStatus.DELETED)
				continue;
			
			switch (userDevice.type) {
				case ANDROID:
					notificationBuilder.addChannel(NotificationChannelType.ANDROID, subscription, subscriptionAdverts);
					wasMobileFound = true;
					break;
				case IOS:
					notificationBuilder.addChannel(NotificationChannelType.IOS, subscription, subscriptionAdverts);
					wasMobileFound = true;
					break;
				case WINDOWS_PHONE:
					// TODO Implement Windows Phone notifications
					logger.warn("This implementation doesn't support Windows Phone notificaiton for now, please implement it");
					break;
				default:
					break;
			}
		}
		
		if (wasMobileFound == false)
			logger.warn("Mobile channel was selected, but used doesn't have any active Mobile devices to notify, please check if it is correct behaviour or not. Subscription.ID is {}", subscription.id);
		
		return wasMobileFound;
	}
	
	
	private boolean addWebChannelsToNotification(Subscription subscription, List<SubscriptionAdvert> subscriptionAdverts, NotificationEventBuilder notificationBuilder) {
		boolean wasFound = false;
		for (UserDevice userDevice : subscription.user.devices) {
			if (userDevice.status == UserDeviceStatus.DELETED)
				continue;
			
			switch (userDevice.type) {
				case CHROME:
					notificationBuilder.addChannel(NotificationChannelType.CHROME, subscription, subscriptionAdverts);
					wasFound = true;
					break;
				case FIREFOX:
					notificationBuilder.addChannel(NotificationChannelType.FIREFOX, subscription, subscriptionAdverts);
					wasFound = true;
					break;
				case OPERA:
					notificationBuilder.addChannel(NotificationChannelType.OPERA, subscription, subscriptionAdverts);
					wasFound = true;
					break;
				case SAFARI:
					notificationBuilder.addChannel(NotificationChannelType.SAFARI, subscription, subscriptionAdverts);
					wasFound = true;
					break;
				default:
					break;
			}
		}
		
		if (wasFound == false)
			logger.warn("Web channel was selected, but used doesn't have any active Mobile devices to notify, please check if it is correct behaviour or not. Subscription.ID is {}", subscription.id);
		
		return wasFound;
	}
	
	
	private List<SubscriptionNotificationChannelType> getRequiredNotificationChannels(Subscription subscription) {
		List<SubscriptionNotificationChannelType> channels = new ArrayList<SubscriptionNotificationChannelType>();
		
		if (subscription.notification.channels == null || 
			(subscription.notification.channels.size() == 1 && 
			 subscription.notification.channels.get(0) == SubscriptionNotificationChannelType.UNSPECIFIED)) {
			// Если сюда зашли значит нужно на все доступные каналы рассылать

			channels.add(SubscriptionNotificationChannelType.EMAIL);
			channels.add(SubscriptionNotificationChannelType.MOBILE);
			channels.add(SubscriptionNotificationChannelType.WEB);

		} else {
			// Если сюда зашли значит чувак выбрал определенные каналы для уведомений

			for (SubscriptionNotificationChannelType channel : subscription.notification.channels) {
				if (channel == SubscriptionNotificationChannelType.UNSPECIFIED)
					continue; // Потому что это на верху обрабатывается и сдесь можно игнорировать
				
				channels.add(channel);
			}
		}
		
		return channels;
	}
	
	/**
	 * Метод проверяет наличие активной подписки в базе или возвращает null
	 * @param ds
	 * @param model
	 * @return
	 */
	private Subscription getActiveSubscription(Datastore ds, SubscriptionNotificationBuilderModel model) {
		Subscription subscription = ds.get(Subscription.class, new ObjectId(model.subscriptionId));

		if (subscription.status != SubscriptionStatus.ACTIVE) {
			logger.debug("Subscription with Id {} is inactive status: {}", subscription.id, subscription.status);
			return null;
		}
		
		if (Calendar.getInstance().compareTo(subscription.startedAt) < 0) {
			// Time is before -> startedAt not yet happenned
			logger.debug("Subscription {} startedAt {} less then current time, will be skipped.", subscription.id, subscription.startedAt);
			return null;
		}
		
		if (Calendar.getInstance().compareTo(subscription.expiresAt) > 0) {
			// Time is before -> startedAt not yet happenned
			logger.debug("Subscription {} expiresAt {} less then current time, will be skipped.", subscription.id, subscription.expiresAt);
			return null;
		}
		
		return subscription;
	}

	
	/**
	 * Возвращает список объявлений в подписках и указанных в сообщение объявлений. Исключает 
	 * те которые были удалены или заменены
	 * @param ds
	 * @param model
	 * @param subscription
	 * @return
	 */
	private List<SubscriptionAdvert> getActiveSubscriptionAdverts(Datastore ds, SubscriptionNotificationBuilderModel model, Subscription subscription) {
		List<SubscriptionAdvert> subscriptionAdverts = new ArrayList<SubscriptionAdvert>();
		
		for (SubscriptionAdvert subscriptionAdvertObject : subscription.adverts) {
			for (String subscriptionAdvertId : model.subscriptionAdvertIds) {
				if (subscriptionAdvertId.equals(subscriptionAdvertObject.id.toString())) {
					
					// Check if it is active one
					if (subscriptionAdvertObject.status != SubscriptionAdvertStatus.DELETED && 
						subscriptionAdvertObject.status != SubscriptionAdvertStatus.REPLACED) {
						subscriptionAdverts.add(subscriptionAdvertObject);
					}
				}
			}
		}
		
		return subscriptionAdverts;
	}
	
}
