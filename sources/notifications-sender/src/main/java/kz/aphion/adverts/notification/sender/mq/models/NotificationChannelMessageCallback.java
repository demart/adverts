package kz.aphion.adverts.notification.sender.mq.models;

import java.util.Calendar;

import com.google.gson.Gson;

/**
 * Уведомление от транспорных систем
 * 
 * @author artem.demidovich
 *
 * Created at Aug 16, 2016
 */
public class NotificationChannelMessageCallback {

	/**
	 * Идентификатор события
	 */
	public String notificationId;
	
	/**
	 * Идентификатор канала 
	 */
	public String channelId;
	
	/**
	 * Время
	 */
	public Calendar time;
	
	/**
	 * Статус 
	 */
	public NotificationStatus status;
	
	/**
	 * Сообщение допольнительная информация
	 */
	public String message;
	
	/**
	 * Компонент системы который отчитался
	 */
	public String system;
	
	/**
	 * Разбирает JSON объект в модель для последующей обработки
	 * @param jsonModel
	 * @return
	 */
	public static NotificationChannelMessageCallback parseModel(String jsonModel) {
		if (jsonModel == null)
			return null;
		
		Gson gson = new Gson();
		NotificationChannelMessageCallback model = gson.fromJson(jsonModel, NotificationChannelMessageCallback.class);
		
		return model;
	}
	
	
	/**
	 * Конвертирует модель в JSON
	 * @param notificationEvent
	 * @return
	 */
	public static String toJson(NotificationChannelMessageCallback notificationEvent) {
		if (notificationEvent == null)
			return null;
		
		Gson gson = new Gson();
		String jsonModel = gson.toJson(notificationEvent);
		
		return jsonModel;
	}
}
