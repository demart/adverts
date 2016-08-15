package kz.aphion.adverts.subscription.live.models;

import java.util.Calendar;

/**
 * Модель подписки для хранения основных параметров
 * @author artem.demidovich
 *
 * Created at Aug 14, 2016
 */
public class SubscriptionModel {

	/**
	 * Идентификатор подписки
	 */
	public String id;
	
	/**
	 * Идентифкатор пользователя
	 */
	public String userId;
	
	/**
	 * Когда запустили подписку
	 */
	public Calendar startedAt;
	
	/**
	 * Время когда остановили или прервали подписку (для слива статистики)
	 */
	public Calendar finishedAt;
	
	/**
	 * Сколько уже отправлено объявлений
	 */
	public Integer advertsCount;
	
	/**
	 * Лимит объявлений (Для бесплатных аккаунтов)
	 */
	public Integer advertsLimit;
	
}
