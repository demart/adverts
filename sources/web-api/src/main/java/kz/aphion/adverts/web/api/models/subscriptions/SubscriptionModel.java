package kz.aphion.adverts.web.api.models.subscriptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kz.aphion.adverts.persistence.subscription.Subscription;
import kz.aphion.adverts.persistence.subscription.SubscriptionAdvertType;
import kz.aphion.adverts.persistence.subscription.SubscriptionStatus;
import kz.aphion.adverts.web.api.models.subscriptions.criteria.SubscriptionCriteriaModel;

/**
 * Модель подписки пользователя
 * @author artem.demidovich
 *
 * Created at Nov 22, 2017
 */
public class SubscriptionModel {
	
	/**
	 * Идентификатор подписки
	 */
	public String id;
	
	/**
	 * Тип подписки
	 */
	public SubscriptionAdvertType advertType;
	
	/**
	 * Время когда подписка была активирована
	 * (Так как запись могли создать но не запустить)
	 */
	public Calendar startedAt;
	
	/**
	 * Время когда подписка будет завершена
	 */
	public Calendar expiresAt;
	
	/**
	 * Параметры уведомлений подписки
	 */
	public SubscriptionNotificationModel notification;
	
	
	/**
	 * Критериии подписки
	 */
	public SubscriptionCriteriaModel criteria;
	
	
	/**
	 * Сколько всего объявлений в подписке
	 */
	public int countTotalAdverts;
	
	/**
	 * Сколько новых объявлений в подписке
	 */
	public int countUnseenAdverts;
	
	
	/**
	 * Статус подписки
	 */
	public SubscriptionStatus status;

	/**
	 * Конвертирует список подписок в модели данных
	 * @param subscriptions
	 * @return
	 */
	public static List<SubscriptionModel> convertToModels(List<Subscription> subscriptions) {
		if (subscriptions == null)
			return null;
		
		List<SubscriptionModel> models = new ArrayList<SubscriptionModel>();
		for (Subscription subscription : subscriptions) {
			SubscriptionModel model = SubscriptionModel.convertToModel(subscription); 
			if (model != null)
				models.add(model);
		}
		
		return models;
	}
	
	public static SubscriptionModel convertToModel(Subscription subscription) {
		 if (subscription == null)
			 return null;
		 
		 SubscriptionModel model = new SubscriptionModel();
		 
		 model.advertType = subscription.advertType;
		 model.countTotalAdverts = 0;
		 model.countUnseenAdverts = 0;
		 model.criteria = SubscriptionCriteriaModel.convertToModel(subscription.criteria);
		 model.expiresAt = subscription.expiresAt;
		 model.id = subscription.id.toHexString();
		 model.notification = SubscriptionNotificationModel.convertToModel(subscription.notification);
		 model.startedAt = subscription.startedAt;
		 model.status = subscription.status;
		 
		 return model;
	}
	
}
