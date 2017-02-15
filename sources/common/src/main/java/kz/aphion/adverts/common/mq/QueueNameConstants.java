package kz.aphion.adverts.common.mq;

public enum QueueNameConstants {

	// ===========================================================
	// ====== QUEUE NAMES FOR PHONES AND RELATED PROJECTS ========
	// ===========================================================	
	
	/**
	 * Очередь для отправки новых телефонов на обработку
	 */
	PHONE_REGISTRATION_QUEUE("adverts.phones.registration"),

	/**
	 * Очередь для проверки установлен ли Viber на телефоне 
	 */
	PHONE_CHECK_VIBER_QUEUE("adverts.phones.check.viber"),
	
	/**
	 * Очередь для проверки установлен ли Telegram на телефоне 
	 */
	PHONE_CHECK_TELEGRAM_QUEUE("adverts.phones.check.telegram"),
	
	/**
	 * Очередь для проверки установлен ли WhatsApp на телефоне 
	 */
	PHONE_CHECK_WHATSAPP_QUEUE("adverts.phones.check.whatsapp"),
	

	// ===========================================================
	// ========== QUEUE NAMES FOR CRAWLERS AND ANALYSER ==========
	// ===========================================================	

	/**
	 * Очередь для отправки новых объявлений в обработку анализатору
	 * ЗДЕСЬ ДЛЯ ТОГО ЧТОБЫ НЕ ЗАБЫТЬ ЕЁ НАЗВАНИЕ
	 */
	ADVERTS_REALTY_ANALYSE_QUEUE("adverts.crawler.realty"),
	
	/**
	 * Название очереди куда писать исходящие сообщения после обработки модулем analyse
	 * На данным момент это очередь модуля подписок. 
	 */
	ADVERTS_REALTY_SUBSCRIPTION_QUEUE("adverts.realty.subscriptions"),

	/**
	 * TEST TEST TEST
	 * Название очереди куда писать исходящие сообщения после обработки модулем analyse
	 * На данным момент это очередь модуля подписок. 
	 */
	ADVERTS_REALTY_SUBSCRIPTION_QUEUE_TEST("adverts.realty.subscriptions.test"),

	/**
	 * Название очереди откуда надо читать сообщения для Live Search
	 */
	ADVERTS_REALTY_SUBSCRIPTION_LIVE_QUEUE("adverts.realty.subscriptions.live"),
	
	
	/**
	 * Очередь подготовки сообщений для отправки в систему уведомлений уведомлений
	 */
	MQ_SUBSCRIPTION_ADVERTS_NOTIFICATION_BUILDER_QUEUE("adverts.subscriptions.notifications.builder"),
		
	/**
	 * Очередь для приема отметок и статусов обработки от системы уведомлений.  
	 */
	MQ_SUBSCRIPTION_NOTIFICATION_CALLBACK_QUEUE("adverts.subscriptions.notifications.callback"),
	
	/**
	 * Очередь куда нужно отправлять задачи для модуля уведомления
	 */
	MQ_NOTIFICATION_QUEUE("adverts.notifications"),
	
	
	
	
	
	
	
	/**
	 * Очередь куда нужно отправлять задачи для модуля уведомления
	 */
	NOTIFICATION_QUEUE("adverts.notifications"),
	
	/**
	 * Очередь для отправки callback от транспорта в систему уведомлений
	 */
	NOTIFICATION_CALLBACK_QUEUE("adverts.notifications.callback"),
	
	/**
	 * Название очереди куда сообщения для немедленной рассылки с помощью модуля notification
	 */
	ADVERTS_IMMEDIATE_NOTIFICATION_QUEUE("adverts.notifications.immediate"),
	
	/**
	 * Очередь для отправки EMAIL
	 */
	NOTIFICATION_EMAIL_QUEUE("adverts.notifications.email"),
	
	/**
	 * Очередь для отправки PUSH
	 */
	NOTIFICATION_PUSH_QUEUE("adverts.notifications.push"),
	
	/**
	 * Очередь для отправки PUSH в браузеры
	 */
	NOTIFICATION_BROWSER_QUEUE("adverts.notifications.browser"),
	
	/**
	 * Очередь для отправки SMS
	 */
	NOTIFICATION_SMS_QUEUE("adverts.notifications.sms");
	
	private final String queueName;
	
	private QueueNameConstants(final String queueName) {
		this.queueName = queueName;
	}
	
	public String getValue() {
		return queueName;
	}
	
	/*
	@Override
	public String toString() {
		return queueName;
	}
	*/
}
