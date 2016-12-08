package kz.aphion.adverts.persistence.subscription.notification;

/**
 * Подтипы отложенных уведолмений
 * 
 * @author artem.demidovich
 *
 * Created at Aug 11, 2016
 */
public enum SubscriptionNotificationScheduledType {

	/**
	 * Запускать по времени
	 */
	TIME,
	
	/**
	 * Запускать по кол-ву новых записей
	 */
	RECORDS,
	
	/**
	 * Запускать по времени или кол-ву записей, что наступит ранее
	 */
	TIME_AND_RECORDS,
}
