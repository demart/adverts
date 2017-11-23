package kz.aphion.adverts.web.api.models.subscriptions;

import java.util.Calendar;

import kz.aphion.adverts.persistence.subscription.SubscriptionAdvertStatus;

/**
 * Модель объявления найденного по подписке пользователя
 * 
 * @author artem.demidovich
 *
 * Created at Nov 22, 2017
 */
public class SubscriptionAdvertModel {

	/**
	 * Идентификатор объявления
	 */
	public String advertId;
	
	/**
	 * Было ли просмотрено объявление пользователем
	 */
	public Boolean hasBeenViewed;
	
	/**
	 * Когда просмотрели объявление
	 */
	public Calendar viewedAt;
	
	/**
	 * Было ли отправлено уведомление (чтобы исключить дублирование)
	 */
	public Boolean wasNotificationSent;
	
	/**
	 * Когда отправили уведомление
	 */
	public Calendar notificationWasSentAt;
	
	/**
	 * Статус объявление в этом результате
	 * Новое
	 * Улучшено
	 * Ухудшено
	 * Возможно будет полезно для 
	 */
	public SubscriptionAdvertStatus status;
	
}
