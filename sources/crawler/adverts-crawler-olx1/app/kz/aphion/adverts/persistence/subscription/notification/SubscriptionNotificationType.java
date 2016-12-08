package kz.aphion.adverts.persistence.subscription.notification;

/**
 * Тип уведомления по подписке
 * @author artem.demidovich
 *
 * Created at Aug 11, 2016
 */
public enum SubscriptionNotificationType {

	/**
	 * Мнгновенные уведомления (без задержек)
	 */
	IMMEDIATE,
	
	/**
	 * Отложенные уведомления
	 */
	SCHEDULED,
}
