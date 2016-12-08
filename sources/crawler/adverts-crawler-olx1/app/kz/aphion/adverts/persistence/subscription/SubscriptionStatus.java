package kz.aphion.adverts.persistence.subscription;

/**
 * Возможные статусы подписки на уведомления
 * 
 * @author artem.demidovich
 *
 * Created at Aug 11, 2016
 */
public enum SubscriptionStatus {

	/**
	 * Неопределенный статус
	 */
	UNDEFINED,
	
	/**
	 * Запущена подписка
	 */
	ACTIVE,
	
	/**
	 * Подготовлена но не запущена.
	 */
	PENDING,
	
	/**
	 * Необходимо внести оплату за подписку
	 */
	PENDING_PAYMENT,
	
	/**
	 * Подписка закончила время выполнения
	 */
	EXPIRED,
	
	/**
	 * Подписка удалена
	 */
	DELETED,
	
	
}
