package kz.aphion.adverts.subscription.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import kz.aphion.adverts.notification.mq.models.NotificationChannel;
import kz.aphion.adverts.notification.mq.models.NotificationChannelType;
import kz.aphion.adverts.notification.mq.models.NotificationEventMessage;
import kz.aphion.adverts.notification.mq.models.NotificationEventSource;
import kz.aphion.adverts.persistence.subscription.Subscription;
import kz.aphion.adverts.persistence.subscription.SubscriptionAdvert;

/**
 * Класс билдер, подготавливает сообщение для отправки в очередь уведомлений для последующей
 * рассылки
 * @author artem.demidovich
 *
 * Created at Mar 6, 2017
 */
public class NotificationEventBuilder {

	private NotificationEventMessage notificationMessage = new NotificationEventMessage();
	
	public NotificationEventBuilder() {
		notificationMessage.accessToken = "TO_BE_DISCUSSED";
		notificationMessage.callbackParameters = new ArrayList<>(); // Empty
		notificationMessage.callbackQueue = null; // Empty
		notificationMessage.eventId = UUID.randomUUID().toString(); // Without storing on this side
		notificationMessage.eventSource = NotificationEventSource.SUBSCRIPTION;
		notificationMessage.eventSourceSystem = "SUBSCRIPTION V1";
		notificationMessage.parameters = new ArrayList<>(); // Empty
		notificationMessage.channels = new ArrayList<>();
	}
	
	public void setUserId(String userId) {
		notificationMessage.userId = userId; // User from Subscription
	}
	
	public void addChannel(NotificationChannel channel) {
		// TODO CHECK IF NOT EXISTS THE SAME CHANNEL OR IT CAN BE MORE THEN ONE?
		notificationMessage.channels.add(channel);
	}
	
	public NotificationEventMessage build() {
		return notificationMessage;
	}
	
	
	/**
	 * Метод формирует сообщение для выбранного канала. Используется шаблонизатор для формирования сообщения
	 * @param channelType
	 * @param subscription
	 * @param subscriptionAdverts
	 */
	public void addChannel(NotificationChannelType channelType, Subscription subscription, List<SubscriptionAdvert> subscriptionAdverts) {
		
		// TODO ADD TEMPLATE ENGINE
		// TODO ADD TEMPLATE STORAGE
		
		// Взять шаблон для указанного типа
		// Взять данные конент и подписку
		// Взять маркетинговый конетнт
		// Сохранить результат шаблонизирования

	}
	
}
