package kz.aphion.adverts.persistence.notification;

import java.util.Calendar;
import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

/**
 * Сущность описывает канал уведомлений клиента. Это могут быть мобиыльные приложения, браузеры
 * почта, веб сокеты и так далее.
 * @author artem.demidovich
 *
 * Created at Aug 15, 2016
 */
public class NotificationChannel {

	/**
	 * Уникальный идентифкатор уведомления для этого канала.
	 * Должен использоваться для получения callback уведомлений от систем транспорта
	 */
	@Property
	public String uid;
	
	/**
	 * Тип канала для отправки уведомления
	 */
	@Property
	public NotificationChannelType type;
	
	/**
	 * Идентификатор адресата.
	 * Например 
	 * 	1. appId для отправки push уведомления
	 *  2. email для отправки почты
	 *  3. browser для отправки push в браузре
	 *  и т.д.
	 */
	@Property
	public String addresseeId;
	
	/**
	 * Заголовок сообщения
	 */
	@Property
	public String title;
	
	/**
	 * Текст сообщения
	 */
	@Property
	public String body;
	
	/**
	 * Общий статус уведомления для конкретного канала. Агрегируется из всех статусов.
	 */
	@Property
	public NotificationStatus status; 
	
	/**
	 * Истрия изменения статусов
	 */
	@Embedded
	public List<NotificationChannelProgressStatus> statuses;
	
	/**
	 * Уведомление для канало создано
	 */
	public Calendar createdAt;
	
	/**
	 * Уведомление для канало обновлено последний раз
	 */
	public Calendar updatedAt;
	
	/**
	 * Список параметров для указаного канала уведомления
	 */
	@Embedded
	public List<NotificationParameter> parameters;
	
}
