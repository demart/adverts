package kz.aphion.adverts.notification.mq;

/**
 * Запрос на рассылку уведомления по указанным параметрам
 * @author artem.demidovich
 *
 * Created at Aug 16, 2016
 */
public class NotificationEvent {

	/**
	 * Доступ
	 */
	public String accessToken;
	
	/**
	 * Идентификатор пользователя (может быть null)
	 */
	public String userId;
	
	/**
	 * Идентификатор события
	 */
	public String eventId;
	
	/**
	 * Источник события
	 */
	public String eventSource;
	
	/**
	 * Идентифкатор системы источника
	 */
	public String eventSourceSystem;
	
	/**
	 * Информация о каналах уведомления
	 */
	public NotificationChannel channels;
	
	/**
	 * Колбэк очердеь для отправки
	 */
	public String callbackQueue;
	
	/**
	 * Список параметров дополительных, возможно часть уйдет обратно в колбэке
	 */
	public String parameters;
	
}
