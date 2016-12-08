package kz.aphion.adverts.subscription.adverts.searcher;

import java.util.Arrays;
import java.util.Calendar;

import kz.aphion.adverts.persistence.subscription.Subscription;
import kz.aphion.adverts.persistence.subscription.SubscriptionAdvert;
import kz.aphion.adverts.persistence.subscription.SubscriptionAdvertStatus;
import kz.aphion.adverts.persistence.subscription.SubscriptionStatus;
import kz.aphion.adverts.persistence.subscription.notification.SubscriptionNotificationScheduledType;
import kz.aphion.adverts.persistence.subscription.notification.SubscriptionNotificationType;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Клас помощник строить запрос на получаение всех необходимых подписок для обработки
 * 
 * @author artem.demidovich
 *
 * Created at Aug 18, 2016
 */
public class SubscriptionAdvertsQueryBuilder {

	private static Logger logger = LoggerFactory.getLogger(SubscriptionAdvertsQueryBuilder.class);
	
	/**
	 * Время с которого можно считать что наступла ночь
	 * 
	 * TODO Вынести в настройки или конфиг файл
	 */
	public static int NIGTH_STARTS_AFTER = 23;
	
	/**
	 * Время с которого можно считать что ночь закончилась
	 * 
	 * TODO Вынести в настройки или конфиг файл
	 */
	public static int NIGTH_ENDS_BEFORE = 9;
	
	public static Query<Subscription> buildSearchQuery(Datastore ds) {
		Query<Subscription> q = ds.createQuery(Subscription.class);
		
		//q.disableValidation();
		
		// Только активные подписки
		q.and(q.criteria("status").equal(SubscriptionStatus.ACTIVE));
		q.and(q.criteria("startedAt").lessThanOrEq(Calendar.getInstance().getTime()));
		q.and(q.criteria("expiresAt").greaterThanOrEq(Calendar.getInstance().getTime()));
		
		// Критерии уведомлений по подпискам

		// Только подписки по расписанию
		q.and(q.criteria("notification.type").equal(SubscriptionNotificationType.SCHEDULED));
				
		// Не беспокоить ночью
		addDoNotDisturbAtNightCriteria(q);
		
		// Подписки у которых подошло время или кол-во записей для проверки
		addScheduledTypeCriteria(q); 
		
		return q;
	}
	
	
	private static Query<Subscription> addScheduledTypeCriteria(Query<Subscription> q) {
		// Добавляем различные критерии в зависимости от выбранного режима пользователем
		
		// Критерии основанные на указанных ниже вычислаяемых полях должны корректно
		// обрабатываться в других местях:
		// 	1. В модуле поиска подписок для добавления объявлений (увелдичение счетчиков)
		//	2. В этом модуле по факту завершения нужно обнулять учеткики раз мы уже их учли
		//	3. В модуле для создания подписок (end-users) должны также конторллировать 
		//		в случае добавления или в случае изменения записей (сбос счетчиков)
		
		q.and(
			q.or(
				// У которых еще не было запуска, но нужно запустить первый раз
				// чтобы потом считать в нормальном режиме
				q.criteria("notification.lastNotifiedTime").doesNotExist(),
				q.criteria("notification.lastNotifiedTime").equal(null),
				
				// Если указан режим уведомления по времени
				q.and(
					q.criteria("notification.scheduledType").equal(SubscriptionNotificationScheduledType.TIME),
					q.criteria("notification.nextSchedulerScanTime").lessThanOrEq(Calendar.getInstance().getTime())
				),
				
				// Если указан режим по уведомлять по кол-ву записей
				q.and(
					q.criteria("notification.scheduledType").equal(SubscriptionNotificationScheduledType.RECORDS),
					q.criteria("notification.isMinimumAmountAchieved").equal(true)
				),
				
				// Если указан режим по времени и кол-ву записей что наступен ранее
				q.and(
					q.criteria("notification.scheduledType").equal(SubscriptionNotificationScheduledType.TIME_AND_RECORDS),
					q.or(
						q.criteria("notification.nextSchedulerScanTime").lessThanOrEq(Calendar.getInstance().getTime()),
						q.criteria("notification.isMinimumAmountAchieved").equal(true)
					)
				)
					
			)	
		);
		
		return q;
	}
	
	private static Query<Subscription> addDoNotDisturbAtNightCriteria(Query<Subscription> q) {
		Calendar time = Calendar.getInstance();
		if (time.get(Calendar.HOUR_OF_DAY) > NIGTH_STARTS_AFTER || time.get(Calendar.HOUR_OF_DAY) < NIGTH_ENDS_BEFORE) {
			// Ночь, уведомляем только тех кто не против, если против то получит утром разом все
			logger.debug("Search will exclude subscriptions with DoNotDisturbAtNightOption, because it is night right now.");
			q.and(
				q.or(
					q.criteria("notification.doNotDisturbAtNight").doesNotExist(),
					q.criteria("notification.doNotDisturbAtNight").equal(false),
					q.criteria("notification.doNotDisturbAtNight").equal(null)
				)
			);
			
		} else {
			// День
			// Тут ничего не делаем так как это день и у нас нету никаких требований по
			// ограничению в дневное время
		}
		
		return q;
	}
	
	
	public static Query<SubscriptionAdvert> buildSubscriptionAdvertsQuery(Datastore ds, Subscription subscription) {
		Query<SubscriptionAdvert> q = ds.createQuery(SubscriptionAdvert.class);
		
		//q.disableValidation();
		
		// Только объявления которые принадлежат данной подписке
		q.and(q.criteria("subscription").equal(subscription));
		
		// Только у которых есть объявление которые нуждаются в уведомлении
		// !!! Отключил, так как поиск работает только в рамках одной коллекции
		addOnlyNotNotifiedYetAdvertsCriteria(q);
		
		return q;
	}
	
	public static Query<SubscriptionAdvert> addOnlyNotNotifiedYetAdvertsCriteria(Query<SubscriptionAdvert> q) {
		// Мы рассматриваем только те подписки у которых есть объявления о которых
		// необходио сообщить пользователю
		q.and(
			q.or(
				q.criteria("wasNotificationSent").equal(false),
				q.criteria("wasNotificationSent").equal(null)
			),
			q.criteria("status").hasNoneOf(
				Arrays.asList(
					SubscriptionAdvertStatus.REPLACED,
					SubscriptionAdvertStatus.DELETED
				)
			)
		);
		return q;
	}
}
