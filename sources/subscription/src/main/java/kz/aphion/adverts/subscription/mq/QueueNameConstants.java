package kz.aphion.adverts.subscription.mq;

public class QueueNameConstants {

	/**
	 * Название очереди куда читать сообщения от анализатора 
	 */
	public static final String MQ_REALTY_ADVERTS_SUBSCRIPTION_QUEUE = "adverts.realty.subscriptions.test";
	
	/**
	 * Очередь подготовки сообщений для отправки в систему уведомлений уведомлений
	 */
	public static final String MQ_SUBSCRIPTION_ADVERTS_NOTIFICATION_BUILDER_QUEUE = "adverts.subscriptions.notifications.builder";
		
	/**
	 * Очередь для приема отметок и статусов обработки от системы уведомлений.  
	 */
	public static final String MQ_SUBSCRIPTION_NOTIFICATION_CALLBACK_QUEUE = "adverts.subscriptions.notifications.callback";
	
	/**
	 * Очередь куда нужно отправлять задачи для модуля уведомления
	 */
	public static final String MQ_NOTIFICATION_QUEUE = "adverts.notifications";
	
}
