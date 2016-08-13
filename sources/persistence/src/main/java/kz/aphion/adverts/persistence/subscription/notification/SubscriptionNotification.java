package kz.aphion.adverts.persistence.subscription.notification;

import java.util.Calendar;
import java.util.List;

import kz.aphion.adverts.persistence.CalendarConverter;

import org.mongodb.morphia.annotations.Converters;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

/**
 * Сущность описывает настройки уведолмений подписки
 * 
 * @author artem.demidovich
 *
 * Created at Aug 11, 2016
 */
@Embedded
@Converters(CalendarConverter.class)
public class SubscriptionNotification {

	/**
	 * Тип уведолмений для пользователя
	 */
	@Property
	public SubscriptionNotificationType type;
	
	/**
	 * Не уведолмять ночью с 23:00 по 09:00 например)
	 */
	@Property
	public Boolean doNotDisturbAtNight;
	
	/**
	 * Отслеживает время подследнего уведомления отправленного пользователю
	 */
	@Property
	public Calendar lastNotifiedTime;
	
	/**
	 * Подвиды отложенных уведомлений
	 */
	@Property
	public SubscriptionNotificationScheduledType scheduledType;
	
	/**
	 * В случае если выбран режим уведомлений по расписанию то 
	 * необходимо запускать уведомления каждые интервалы времени<br>
	 * Время указывается в минутах, поэтому если необходимо указать раз в час<br>
	 * Every 60 mins<br>
	 * Every 720 mins - 12 hours<br>
	 * etc.<br>
	 * Если < 1 то не учитывается при просмотре, по умолчанию берется в расчет scheduledRunAfter
	 */
	@Property
	public Integer scheduledRunEvery;
	
	/**
	 * В случае если выбран режим уведомлений по расписанию то
	 * опиционально можно указать сколько необходимо записей чтобы отправить уведомление
	 * заранее.
	 * Например:
	 * 	Пользователь указан раз в 1 час уведомлять, но при этом указал что если будет
	 * 10 новых записей в подписке, то уведомить заранее.
	 * Если меньше 1 то не учитывается
	 */
	@Property
	public Integer scheduledRunAfter;
	
	/**
	 * Разрешенные каналы рассылки уведолмений, если ниуказан ни один тип или указан UNSPECIFIED
	 * то будут учитываьтся все каналы которые есть у пользователя.
	 * Если указаны другие каналы, то будут рассматриваться только они.
	 * Если указан UNSPECIFIED и MOBILE, по умолчанию считается что пользователь выбрал ТОЛЬКО
	 * канал MOBILE (в дальнейшем логику можно изменить)
	 */
	@Embedded
	public List<SubscriptionNotificationChannelType> channels;
}
