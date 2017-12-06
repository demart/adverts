package kz.aphion.adverts.web.api.models.subscriptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.subscription.SubscriptionAdvert;
import kz.aphion.adverts.persistence.subscription.SubscriptionAdvertStatus;

/**
 * Модель объявления найденного по подписке пользователя
 * 
 * @author artem.demidovich
 *
 * Created at Nov 22, 2017
 */
public class SubscriptionAdvertModel {

	/**
	 * Идентификатор объявления
	 */
	public String subscriptionAdvertId;
	
	/**
	 * Объект объявления
	 */
	public BaseEntity advert;
	
	/**
	 * Было ли просмотрено объявление пользователем
	 */
	public Boolean hasBeenViewed;
	
	/**
	 * Когда просмотрели объявление
	 */
	public Calendar viewedAt;
	
	/**
	 * Было ли отправлено уведомление (чтобы исключить дублирование)
	 */
	public Boolean wasNotificationSent;
	
	/**
	 * Когда отправили уведомление
	 */
	public Calendar notificationWasSentAt;
	
	/**
	 * Статус объявление в этом результате
	 * Новое
	 * Улучшено
	 * Ухудшено
	 * Возможно будет полезно для 
	 */
	public SubscriptionAdvertStatus status;

	public static List<SubscriptionAdvertModel> convertToModels(List<SubscriptionAdvert> adverts) {
		if (adverts == null || adverts.size() == 0)
			return null;
		
		List<SubscriptionAdvertModel> models = new ArrayList<SubscriptionAdvertModel>();
		for (SubscriptionAdvert advert : adverts) {
			SubscriptionAdvertModel model = covertToModel(advert);
			if (model != null)
				models.add(model);
		}
		
		return models;
	}
	
	public static SubscriptionAdvertModel covertToModel(SubscriptionAdvert advert) {
		if (advert == null)
			return null;
		
		SubscriptionAdvertModel model = new SubscriptionAdvertModel();
		
		model.subscriptionAdvertId = advert.id.toHexString();
		model.advert = advert.advert;
		model.hasBeenViewed = advert.hasBeenViewed;
		model.notificationWasSentAt = advert.notificationWasSentAt;
		model.status = advert.status;
		model.viewedAt = advert.viewedAt;
		model.wasNotificationSent = advert.wasNotificationSent;
		
		return model;
	}
	
}
