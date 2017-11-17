package kz.aphion.adverts.notification.mq.models;

import java.util.Calendar;
import java.util.List;


/**
 * Уведомление в систему источник
 * 
 * @author artem.demidovich
 *
 * Created at Aug 16, 2016
 */
public class NotificationEventMessageCallback {

	/**
	 * Идентификатор события
	 */
	public String eventId;
	
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
	 * Дополнительные параметры которые необходимо отправить системе источнику события
	 */
	public List<NotificationParameter> parameters;
	
}
