package kz.aphion.adverts.notification.mq.models;

import java.util.List;

import com.google.gson.Gson;

/**
 * Запрос на рассылку уведомления по указанным параметрам
 * @author artem.demidovich
 *
 * Created at Aug 16, 2016
 */
public class NotificationEventMessage {

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
	public NotificationEventSource eventSource;
	
	/**
	 * Идентифкатор системы источника
	 */
	public String eventSourceSystem;
	
	/**
	 * Информация о каналах уведомления
	 */
	public List<NotificationChannel> channels;
	
	/**
	 * Колбэк очердеь для отправки
	 */
	public String callbackQueue;
	
	/**
	 * Список параметров дополительных обратно в колбэке
	 */
	public List<NotificationParameter> callbackParameters;
	
	/**
	 * Список параметров дополительных
	 */
	public List<NotificationParameter> parameters;
	
	
	/**
	 * Разбирает JSON объект в модель для последующей обработки
	 * @param jsonModel
	 * @return
	 */
	public static NotificationEventMessage parseModel(String jsonModel) {
		if (jsonModel == null)
			return null;
		
		Gson gson = new Gson();
		NotificationEventMessage model = gson.fromJson(jsonModel, NotificationEventMessage.class);
		
		return model;
	}
	
	
	/**
	 * Конвертирует модель в JSON
	 * @param notificationEvent
	 * @return
	 */
	public static String toJson(NotificationEventMessage notificationEvent) {
		if (notificationEvent == null)
			return null;
		
		Gson gson = new Gson();
		String jsonModel = gson.toJson(notificationEvent);
		
		return jsonModel;
	}
	
	
	public String toJson() {
		return toJson(this);
	}
	
}
