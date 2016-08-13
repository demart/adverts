package kz.aphion.adverts.subscription.mq;

public class QueueNameConstants {

	/**
	 * Название очереди куда читать сообщения от анализатора 
	 */
	public static final String MQ_REALTY_ADVERTS_SUBSCRIPTION_QUEUE = "adverts.realty.subscriptions.test";
	
	/**
	 * Название очереди куда надо писать сообщения для Live Search
	 */
	public static final String MQ_REALTY_ADVERTS_SUBSCRIPTION_LIVE_QUEUE = "adverts.realty.subscriptions.live";
	
	/**
	 * Название очереди куда сообщения для немедленной рассылки с помощью модуля notification
	 */
	public static final String MQ_ADVERTS_IMMEDIATE_NOTIFICATION_QUEUE = "adverts.notifications.immediate";
	
}
