package kz.aphion.adverts.web.api.models.subscriptions.requests;

import java.util.Map;

import kz.aphion.adverts.persistence.subscription.SubscriptionAdvertType;
import kz.aphion.adverts.web.api.exceptions.ModelValidationException;

/**
 * Модель создания подписки
 * 
 * @author artem.demidovich
 *
 * Created at Dec 6, 2017
 */
public class CreateOrUpdateSubscriptionRequestModel {
	
	/**
	 * Идентификатор подписки
	 */
	public String id;
	
	/**
	 * Тип подписки
	 */
	public SubscriptionAdvertType advertType;
	
	/**
	 * Параметры уведомлений подписки
	 */
	public CreateOrUpdateSubscriptionNotificationRequestModel notification;
	
	/**
	 * Список критериев подписки
	 */
	public Map<String, Object> criteria;

	
	public static void validate(CreateOrUpdateSubscriptionRequestModel model) throws ModelValidationException {
		if (model == null)
			throw new ModelValidationException("model is null");
		
		model.validate();
	}
	
	public void validate() throws ModelValidationException {
		if (advertType == null)
			throw new ModelValidationException("model.advertType is null or empty");
		
		CreateOrUpdateSubscriptionNotificationRequestModel.validate(notification);
		
		if (criteria == null)
			throw new ModelValidationException("model.criteria is null or empty");
		// TODO Add validation of criteria key/value object
		
	}
	
}
