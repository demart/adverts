package kz.aphion.adverts.persistence.subscription;

import java.util.Calendar;
import java.util.List;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.CalendarConverter;
import kz.aphion.adverts.persistence.subscription.criteria.SubscriptionCriteria;
import kz.aphion.adverts.persistence.subscription.notification.SubscriptionNotification;
import kz.aphion.adverts.persistence.users.User;

import org.mongodb.morphia.annotations.Converters;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

/**
 * Сущность описывает список подписок пользователя
 * @author artem.demidovich
 *
 * Created at Aug 11, 2016
 */
@Entity("adverts.users.subscriptions")
@Converters(CalendarConverter.class)
public class Subscription extends BaseEntity {

	/**
	 * Ссылка на пользователя к которому принадлежит данная подписка
	 */
	@Reference
	public User user;
	
	/**
	 * Категория подписок
	 */
	@Property
	public SubscriptionAdvertType advertType;

	/**
	 * Время когда подписка была активирована
	 * (Так как запись могли создать но не запустить)
	 */
	@Property
	public Calendar startedAt;
	
	/**
	 * Время когда подписка будет завершена
	 */
	@Property
	public Calendar expiresAt;
	
	/**
	 * Параметры уведомлений подписки
	 */
	@Embedded
	public SubscriptionNotification notification;
	
	/**
	 * Параметры поиска совпадений в объявлениях.
	 * Здесь указан базовый класс, так как остальные наследники имеют разные параметры поиска
	 */
	@Embedded
	public SubscriptionCriteria criteria;
	
	/**
	 * Содержится список всех найденных подходящих результатов
	 */
	@Reference
	public List<SubscriptionAdvert> adverts;
	
	/**
	 * Статус подписки
	 */
	@Property
	public SubscriptionStatus status;
	
}