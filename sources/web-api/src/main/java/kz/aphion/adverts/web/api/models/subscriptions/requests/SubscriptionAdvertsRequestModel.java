package kz.aphion.adverts.web.api.models.subscriptions.requests;

import kz.aphion.adverts.web.api.exceptions.ModelValidationException;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

/**
 * Модель запроса на получение списка мета информации об объявлениях в подписке
 * @author artem.demidovich
 *
 * Created at Nov 23, 2017
 */
public class SubscriptionAdvertsRequestModel {
	
	/**
	 * Идентификатор подписки
	 */
	public String subscriptionId;
	
	/**
	 * Для постраничной выгрузки
	 */
	public Integer offset;
	
	/**
	 * Для постраничной выгрузки
	 */
	public Integer limit;

	public static void validate(SubscriptionAdvertsRequestModel model) throws ModelValidationException {
		if (model == null)
			throw new ModelValidationException("model is null");
		
		model.validate();
	}
	
	public void validate() throws ModelValidationException {
		if (StringUtils.isBlank(subscriptionId))
			throw new ModelValidationException("model.subscriptionId is null or empty");
		
		if (!ObjectId.isValid(subscriptionId))
			throw new ModelValidationException("model.subscriptionId is incorrect");

	}
	
	
}
