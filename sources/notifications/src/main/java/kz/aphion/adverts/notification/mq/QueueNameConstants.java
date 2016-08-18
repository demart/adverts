package kz.aphion.adverts.notification.mq;

public class QueueNameConstants {

	/**
	 * Очередь куда нужно отправлять задачи для модуля уведомления
	 */
	public static final String MQ_NOTIFICATION_QUEUE = "adverts.notifications";
	
	/**
	 * Очередь для отправки callback от транспорта в систему уведомлений
	 */
	public static final String MQ_NOTIFICATION_CALLBACK_QUEUE = "adverts.notifications.callback";
	
	
	/**
	 * Название очереди куда сообщения для немедленной рассылки с помощью модуля notification
	 */
	public static final String MQ_ADVERTS_IMMEDIATE_NOTIFICATION_QUEUE = "adverts.notifications.immediate";
	
	/**
	 * Очередь для отправки EMAIL
	 */
	public static final String MQ_NOTIFICATION_EMAIL_QUEUE = "adverts.notifications.email";
	
	/**
	 * Очередь для отправки PUSH
	 */
	public static final String MQ_NOTIFICATION_PUSH_QUEUE = "adverts.notifications.push";
	
	/**
	 * Очередь для отправки PUSH в браузеры
	 */
	public static final String MQ_NOTIFICATION_BROWSER_QUEUE = "adverts.notifications.browser";
	
	/**
	 * Очередь для отправки SMS
	 */
	public static final String MQ_NOTIFICATION_SMS_QUEUE = "adverts.notifications.sms";
	
}
