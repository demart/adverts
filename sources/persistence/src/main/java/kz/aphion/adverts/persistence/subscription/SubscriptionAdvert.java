package kz.aphion.adverts.persistence.subscription;

import java.util.Calendar;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.CalendarConverter;

import org.mongodb.morphia.annotations.Converters;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

/**
 * Запись найденного объекта добавлленого в результаты подписки
 * 
 * @author artem.demidovich
 *
 * Created at Aug 11, 2016
 */
@Entity("adverts.users.subscriptions.adverts")
@Converters(CalendarConverter.class)
public class SubscriptionAdvert extends BaseEntity {

	/**
	 * Ссылка на подписку к которой принадлежит объект
	 */
	@Reference
	public Subscription subscription;
	
	/**
	 * Было ли просмотрено объявление пользователем
	 */
	@Property
	public Boolean hasBeenViewed;
	
	/**
	 * Когда просмотрели объявление
	 */
	@Property
	public Calendar viewedAt;
	
	/**
	 * Было ли отправлено уведомление (чтобы исключить дублирование)
	 */
	@Property
	public Boolean wasNotificationSent;
	
	/**
	 * Когда отправили уведомление
	 */
	@Property
	public Calendar notificationWasSentAt;
	
	/**
	 * Статус объявление в этом результате
	 * Новое
	 * Улучшено
	 * Ухудшено
	 * Возможно будет полезно для 
	 */
	@Property
	public SubscriptionAdvertStatus status;
	
	/**
	 * Ссылка на объявление
	 * Не знаю заработает так или нет
	 */
	@Reference
	public BaseEntity advert;
	
}
