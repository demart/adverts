package kz.aphion.adverts.notification.sender.mq.models;

import java.util.List;

import com.google.gson.Gson;

/**
 * Сообщение которое отправляется каналу
 * @author artem.demidovich
 *
 * Created at Aug 16, 2016
 */
public class NotificationChannelMessage {

	/**
	 * Идентификатор уведомления
	 */
	public String notificationId;
	
	/**
	 * Идентификатор канала
	 */
	public String channelId;
	
	/**
	 * Канал для уведомления
	 */
	public NotificationChannelType type;
	
	/**
	 * Идентификатор уведомления
	 */
	public String addreseeId;
	
	/**
	 * Заголовок для уведомления
	 */
	public String title;
	
	/**
	 * Текст сообщения
	 */
	public String content;
	
	/**
	 * Список дополнительный параметров
	 */
	public List<NotificationParameter> parameters;
	
	
	/**
	 * Разбирает JSON объект в модель для последующей обработки
	 * @param jsonModel
	 * @return
	 */
	public static NotificationChannelMessage parseModel(String jsonModel) {
		if (jsonModel == null)
			return null;
		
		Gson gson = new Gson();
		NotificationChannelMessage model = gson.fromJson(jsonModel, NotificationChannelMessage.class);
		
		return model;
	}
	
	
	/**
	 * Конвертирует модель в JSON
	 * @param notificationEvent
	 * @return
	 */
	public static String toJson(NotificationChannelMessage notificationEvent) {
		if (notificationEvent == null)
			return null;
		
		Gson gson = new Gson();
		String jsonModel = gson.toJson(notificationEvent);
		
		return jsonModel;
	}

}
