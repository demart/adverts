package kz.aphion.adverts.notification.mq.models;

import java.util.List;

/**
 * Информация о канале уведомления
 * @author artem.demidovich
 *
 * Created at Aug 16, 2016
 */
public class NotificationChannel {

	/**
	 * Идентификатор канала
	 */
	public String uid;
	
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
	
}
