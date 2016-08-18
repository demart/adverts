package kz.aphion.adverts.notification.sender.mq;

public class QueueNameConstants {

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
	
	/**
	 * Очередь для отправки callback от транспорта в систему уведомлений
	 */
	public static final String MQ_NOTIFICATION_CALLBACK_QUEUE = "adverts.notifications.callback";
	
}
