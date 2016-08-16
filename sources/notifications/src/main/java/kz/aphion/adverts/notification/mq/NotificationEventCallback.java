package kz.aphion.adverts.notification.mq;

/**
 * Уведомление в систему источник
 * 
 * @author artem.demidovich
 *
 * Created at Aug 16, 2016
 */
public class NotificationEventCallback {

	public String eventId;
	
	public String channelType; // Возможно и не надо, так как есть channelId
	
	public String channelId;
	
	public String parameters;
	
	/**
	 * Статус
	 */
	public String status;
	
	/**
	 * Время
	 */
	public String statusTime;
	
}
