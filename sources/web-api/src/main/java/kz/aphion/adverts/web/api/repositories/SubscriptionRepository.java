package kz.aphion.adverts.web.api.repositories;

import java.util.List;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.persistence.subscription.Subscription;
import kz.aphion.adverts.persistence.subscription.SubscriptionStatus;
import kz.aphion.adverts.persistence.users.User;

/**
 * Репозиторий для работы с подписками пользователя
 * @author artem.demidovich
 *
 * Created at Nov 22, 2017
 */
public class SubscriptionRepository {

	
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
	
}
