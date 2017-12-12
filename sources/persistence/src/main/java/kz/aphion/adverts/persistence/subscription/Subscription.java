package kz.aphion.adverts.persistence.subscription;

import java.util.Calendar;
import java.util.HashMap;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.CalendarConverter;
import kz.aphion.adverts.persistence.adverts.AdvertOperationType;
import kz.aphion.adverts.persistence.adverts.AdvertType;
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
	public AdvertType advertType;

	/**
	 * Подвиды категорий (зависят от категории)
	 */
	@Property
	public String advertSubType;
	
	/**
	 * Тип операции объявления (продажа, аренда, обмен)
	 */
	@Property
	public AdvertOperationType operationType;
	
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
	public HashMap<String, Object> criteria;

	/**
	 * Статус подписки
	 */
	@Property
	public SubscriptionStatus status;
	
}