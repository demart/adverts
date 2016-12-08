package kz.aphion.adverts.persistence.notification;

import java.util.List;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.CalendarConverter;
import kz.aphion.adverts.persistence.users.User;

import org.mongodb.morphia.annotations.Converters;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

/**
 * Сущность описывает уведомление по какому ли событию.
 * 
 * @author artem.demidovich
 *
 * Created at Aug 15, 2016
 */
@Entity("adverts.users.notifications")
@Converters(CalendarConverter.class)
public class Notification extends BaseEntity {

	/**
	 * Идентифкатор события в рамках которого было сгенерировано уведомление.
	 * Событие находится вне рамок системы уведомлений. Поэтому событием считается 
	 * что-то что происходнит в бизнес приложениях. <br>
	 * Например<br>
	 *  Подписки на уведомления недвижимости<br>
	 *  Получение рассылки о каком либо контенте (Новости, Изменения и так далее)
	 *  Получение уведомления из системы биллинга
	 * Везде генерируется какое либо событие котороре требует отправки уведомлений.
	 */
	@Property
	public String eventId;
	
	/**
	 * Источник события уведомления. 
	 * Биллинг, Подписки, Новости и др бизнес подсистемы
	 */
	@Property
	public NotificationEventSource eventSource;
	
	/**
	 * Название системы источника (возможно идентификаторы и другие полезные данные)
	 */
	@Property
	public String eventSourceSystem;
	
	/**
	 * Информация об отправки callback в систему источника
	 */
	@Embedded
	public NotificationCallback callback;
	
	/**
	 * Уведомление для каждого канала с собственными параметрами и контентом
	 */
	@Embedded
	public List<NotificationChannel> channels;
	
	/**
	 * Общий статус уведомления. Так как существует разное кол-во каналов то это значение
	 * должно агрегироваться и не меняться в последующем в меньшую сторону.
	 * Например: Мы получили отметку от одного канала что отправлено, хотя остальные еще не выслали
	 * то общий статус должен быть отправлено.
	 * Если на одном канале человек просмотрел уведомление то общий статус должен быть просмотрен
	 * Пока такая логика, дальше посморим.
	 */
	@Property
	public NotificationProcessStatus progressStatus;
	
	/**
	 * Пользователь для которого предназначено данное уведомление.
	 * В некоторых случаях информации о пользователе не будет.
	 * Например
	 * 	1. Анонимные рассылки для всех пользователей
	 * 	2. Рекламные рассылки для всех у кого включены PUSH уведомления или другие каналы
	 */
	@Reference
	public User user;
	
	/**
	 * Статус записи уведомления в БД
	 */
	@Property
	public NotificationStatus status;
	
}
