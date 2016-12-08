package kz.aphion.adverts.persistence.subscription.notification;

/**
 * Разрешенные каналы для отправки уведомлений
 * 
 * @author artem.demidovich
 *
 * Created at Aug 11, 2016
 */
public enum SubscriptionNotificationChannelType {

	/**
	 * Не указано, будут использоваться все
	 */
	UNSPECIFIED,
	
	/**
	 * Уведомлять PUSH уведомления
	 */
	MOBILE,
	
	/**
	 * Уведомлениять браузеры
	 */
	WEB,
	
	/**
	 * Уведомлять по почте
	 */
	EMAIL
}
