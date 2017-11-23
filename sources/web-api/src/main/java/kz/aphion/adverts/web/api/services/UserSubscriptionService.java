package kz.aphion.adverts.web.api.services;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;

import kz.aphion.adverts.persistence.subscription.Subscription;
import kz.aphion.adverts.persistence.users.User;
import kz.aphion.adverts.web.api.exceptions.AccessDeniedException;
import kz.aphion.adverts.web.api.models.DataListWrapper;
import kz.aphion.adverts.web.api.models.ResponseWrapper;
import kz.aphion.adverts.web.api.models.subscriptions.SubscriptionModel;
import kz.aphion.adverts.web.api.models.subscriptions.requests.UserSubscriptionsRequestModel;
import kz.aphion.adverts.web.api.repositories.SubscriptionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Сервис для работы с подписками пользователя
 * @author artem.demidovich
 *
 * Created at Nov 22, 2017
 */
public class UserSubscriptionService extends BaseSecuredService {

	private static Logger logger = LoggerFactory.getLogger(UserSubscriptionService.class);

	public static int SUBSCRIPTIONS_PAGINATION_MAX_LIMIT = 50;
	
	
	@Inject
	SubscriptionRepository repository;
	
	
	public ResponseWrapper getUserSubscriptions(UserSubscriptionsRequestModel model) throws AccessDeniedException {
		// Get User
		User user = getUserOrThrowException();
		
		// Check model is null
		model = model == null ? new UserSubscriptionsRequestModel() : model;
		
		// Pagination
		int offset = model.offset == null ? 0 : model.offset;
		int limit = (model.limit == null || model.limit < 1) ? 25 : (model.limit > SUBSCRIPTIONS_PAGINATION_MAX_LIMIT ? 25 : model.limit);
		
		// Get Subscriptions
		List<Subscription> subscriptions = repository.getSubscriptions(user, offset, limit);
		
		// Convert To Models
		List<SubscriptionModel> models = SubscriptionModel.convertToModels(subscriptions);
		
		// Get counters	
		long totalCount = repository.getSubscriptionCount(user);
		long page = (offset < limit || limit == 0) ? 1 : (offset / limit) + 1; 
		long pages = (totalCount + limit -1) / limit;
		
		// convert to list model
		DataListWrapper listWrapper = DataListWrapper.with(models, totalCount, page, pages);
		
		return ResponseWrapper.with(Status.OK, listWrapper, "Operation successfully completed");
	}
	
}
