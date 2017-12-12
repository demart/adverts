package kz.aphion.adverts.web.api.repositories;

import java.util.Calendar;
import java.util.List;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.persistence.subscription.Subscription;
import kz.aphion.adverts.persistence.subscription.SubscriptionAdvert;
import kz.aphion.adverts.persistence.subscription.SubscriptionAdvertStatus;
import kz.aphion.adverts.persistence.subscription.SubscriptionStatus;
import kz.aphion.adverts.persistence.users.User;
import kz.aphion.adverts.web.api.exceptions.DataValidationException;
import kz.aphion.adverts.web.api.exceptions.RecordNotFoundException;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Репозиторий для работы с подписками пользователя
 * @author artem.demidovich
 *
 * Created at Nov 22, 2017
 */
public class SubscriptionRepository {
	
	private static Logger logger = LoggerFactory.getLogger(SubscriptionRepository.class);

	/**
	 * Получить подписку по id
	 * @param id
	 * @return
	 * @throws DataValidationException
	 * @throws RecordNotFoundException
	 */
	public Subscription getSubscription(String id) throws DataValidationException, RecordNotFoundException {
		List<Subscription> subscriptions = DB.DS().createQuery(Subscription.class).field("_id").equal(new ObjectId(id)).asList();
		
		if (subscriptions == null || subscriptions.size() == 0) {
			throw new RecordNotFoundException("Subscription not found");
		}
		
		if (subscriptions.size() > 1) {
			// TODO Strange 
			logger.error("USR001E: Error, found more than one subscription.id [{}], which should be unqiue!", id);
			throw new DataValidationException("Duplicate records found!");
		}
		
		Subscription subscription = subscriptions.get(0);
		if (subscription == null) {
			logger.error("USRR002E: Error, subscription.id [{}] is null in non null collection!", id);
			throw new DataValidationException("Subscription not found!");
		}
		
		return subscription;
	}
	
	
	public List<Subscription> getSubscriptions(User user, int offset, int limit) {
		return DB.DS().createQuery(Subscription.class)
			.field("user").equal(user)
			.field("status").notEqual(SubscriptionStatus.DELETED)
			.offset(offset)
			.limit(limit)
			.order("-expiresAt")
			.asList();
	}

	
	public long getSubscriptionCount(User user) {
		return DB.DS().createQuery(Subscription.class)
				.field("user").equal(user)
				.field("status").notEqual(SubscriptionStatus.DELETED)
				.countAll();
	}
	
	/**
	 * Сколько объявлений в подписке
	 * @param subscription
	 * @return
	 */
	public long getSubscriptionAdvertsTotalCount(Subscription subscription) {
		return DB.DS().createQuery(SubscriptionAdvert.class)
				.field("subscription").equal(subscription)
				.countAll();
	}
	
	/**
	 * Сколько не просмотренный объявлений в подписке пользователем
	 * @param subscription подписка
	 * @return
	 */
	public long getSubscriptionAdvertsUnseenCount(Subscription subscription) {
		return DB.DS().createQuery(SubscriptionAdvert.class)
				.field("subscription").equal(subscription)
				.field("hasBeenViewed").equal(false)
				.countAll();
	}


	public List<SubscriptionAdvert> getSubscriptionAdverts(Subscription subscription, int offset, int limit) {
		return DB.DS().createQuery(SubscriptionAdvert.class)
				.field("subscription").equal(subscription)
				.field("status").notEqual(SubscriptionAdvertStatus.DELETED)
				.offset(offset)
				.limit(limit)
				.order("-created")
				.asList();
		
	}


	public long getSubscriptionAdcertsCount(Subscription subscription) {
		return DB.DS().createQuery(SubscriptionAdvert.class)
				.field("subscription").equal(subscription)
				.field("status").notEqual(SubscriptionAdvertStatus.DELETED)
				.countAll();
	}


	public SubscriptionAdvert getSubscriptionAdvert(String subscriptionAdvertId) throws RecordNotFoundException, DataValidationException {
		List<SubscriptionAdvert> adverts = DB.DS().createQuery(SubscriptionAdvert.class)
				.field("_id").equal(new ObjectId(subscriptionAdvertId))
				.field("status").notEqual(SubscriptionAdvertStatus.DELETED)
				.asList();
		
		if (adverts == null || adverts.size() == 0) {
			throw new RecordNotFoundException("SubscriptionAdvert not found");
		}
		
		if (adverts.size() > 1) {
			// TODO Strange 
			logger.error("USR001E: Error, found more than one subscriptionAdvert.id [{}], which should be unqiue!", subscriptionAdvertId);
			throw new DataValidationException("Duplicate records found!");
		}
		
		SubscriptionAdvert advert = adverts.get(0);
		if (advert == null) {
			logger.error("USRR002E: Error, subscriptionAdvert.id [{}] is null in non null collection!", subscriptionAdvertId);
			throw new DataValidationException("Subscription not found!");
		}
		
		return advert;
	}
	
	
	/**
	 * Сохраняет подписку и заполняет базовые поля Created, Updated, Modifier etc
	 * @param subscription
	 */
	public void saveSubscription(Subscription subscription) {
		subscription.modifier = subscription.user.email;
		subscription.created = Calendar.getInstance();
		subscription.updated = Calendar.getInstance();
		DB.DS().save(subscription);
	}
	
}
