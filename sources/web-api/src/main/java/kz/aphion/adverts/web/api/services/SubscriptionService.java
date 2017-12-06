package kz.aphion.adverts.web.api.services;

import java.util.Calendar;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response.Status;

import kz.aphion.adverts.persistence.subscription.Subscription;
import kz.aphion.adverts.persistence.subscription.SubscriptionAdvert;
import kz.aphion.adverts.persistence.subscription.SubscriptionAdvertType;
import kz.aphion.adverts.persistence.subscription.SubscriptionStatus;
import kz.aphion.adverts.persistence.subscription.notification.SubscriptionNotification;
import kz.aphion.adverts.persistence.users.User;
import kz.aphion.adverts.web.api.exceptions.AccessDeniedException;
import kz.aphion.adverts.web.api.exceptions.DataValidationException;
import kz.aphion.adverts.web.api.exceptions.RecordNotFoundException;
import kz.aphion.adverts.web.api.models.DataListWrapper;
import kz.aphion.adverts.web.api.models.ResponseWrapper;
import kz.aphion.adverts.web.api.models.subscriptions.SubscriptionAdvertModel;
import kz.aphion.adverts.web.api.models.subscriptions.SubscriptionModel;
import kz.aphion.adverts.web.api.models.subscriptions.requests.CreateOrUpdateSubscriptionRequestModel;
import kz.aphion.adverts.web.api.models.subscriptions.requests.SubscriptionAdvertsRequestModel;
import kz.aphion.adverts.web.api.models.subscriptions.requests.SubscriptionsRequestModel;
import kz.aphion.adverts.web.api.repositories.SubscriptionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Сервис для работы с подписками пользователя
 * @author artem.demidovich
 *
 * Created at Nov 22, 2017
 */
@Named
@RequestScoped
public class SubscriptionService extends BaseSecuredService {

	private static Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

	public static int SUBSCRIPTIONS_PAGINATION_MAX_LIMIT = 50;
	
	
	@Inject
	SubscriptionRepository repository;
	
	
	public ResponseWrapper getSubscriptions(SubscriptionsRequestModel model) throws AccessDeniedException {
		// Get User
		User user = getUserOrThrowException();
		
		// Check model is null
		model = model == null ? new SubscriptionsRequestModel() : model;
		
		// Pagination
		int offset = model.offset == null ? 0 : model.offset;
		int limit = (model.limit == null || model.limit < 1) ? 25 : (model.limit > SUBSCRIPTIONS_PAGINATION_MAX_LIMIT ? 25 : model.limit);
		
		// Get Subscriptions
		List<Subscription> subscriptions = repository.getSubscriptions(user, offset, limit);
		
		// Convert To Models (bad idea and implementation to put repository in views...)
		List<SubscriptionModel> models = SubscriptionModel.convertToModels(subscriptions, repository);
		
		// Get counters	
		long totalCount = repository.getSubscriptionCount(user);
		long page = (offset < limit || limit == 0) ? 1 : (offset / limit) + 1; 
		long pages = (totalCount + limit -1) / limit;
		
		// convert to list model
		DataListWrapper listWrapper = DataListWrapper.with(models, totalCount, page, pages);
		
		return ResponseWrapper.with(Status.OK, listWrapper, "Operation successfully completed");
	}
	
	
	public ResponseWrapper getSubscriptionDetails(String subscriptionId) throws AccessDeniedException, DataValidationException, RecordNotFoundException {
		// Get User
		User user = getUserOrThrowException();
		
		// Get Subscription
		Subscription subscription = repository.getSubscription(subscriptionId);
		
		// Security check
		if (subscription.user == null)
			throw new DataValidationException("Subscription user is null or empty");
		if (!subscription.user.id.equals(user.id))
			throw new AccessDeniedException("Subscription doesn't belong to the user");
		
		// Covert to model
		SubscriptionModel model = SubscriptionModel.convertToModel(subscription, repository);
		
		return ResponseWrapper.with(Status.OK, model, "Operation successfully completed");
	}
	
	
	public ResponseWrapper createSubscription(CreateOrUpdateSubscriptionRequestModel model) throws AccessDeniedException, DataValidationException {
		// Get User
		User user = getUserOrThrowException();
		
		if (model.advertType != SubscriptionAdvertType.REALTY)
			throw new DataValidationException("Unsupported subscription advert type");
		
		// create subscription
		Subscription subscription = new Subscription();
		subscription.user = user;
		subscription.advertType = model.advertType;
		subscription.status = SubscriptionStatus.PENDING;
		subscription.startedAt = null;
		subscription.expiresAt = null;
		
		// create subscription notification 
		subscription.notification = new SubscriptionNotification();
		subscription.notification.type = model.notification.type;
		subscription.notification.channels = model.notification.channels;
		subscription.notification.doNotDisturbAtNight = model.notification.doNotDisturbAtNight;
		subscription.notification.scheduledType = model.notification.scheduledType;	

		switch (subscription.notification.scheduledType) {
			case RECORDS:
				subscription.notification.scheduledRunAfter = model.notification.scheduledRunAfter == null ? 10 : model.notification.scheduledRunAfter;
				subscription.notification.scheduledRunEvery = 60;
				break;
			case TIME:
				subscription.notification.scheduledRunAfter = 10;
				subscription.notification.scheduledRunEvery = model.notification.scheduledRunEvery == null ? 60 * 2 : model.notification.scheduledRunEvery;
				break;
			case TIME_AND_RECORDS:
				subscription.notification.scheduledRunAfter = model.notification.scheduledRunAfter == null ? 10 : model.notification.scheduledRunAfter;
				subscription.notification.scheduledRunEvery = model.notification.scheduledRunEvery == null ? 60 * 2 : model.notification.scheduledRunEvery;
				break;
			default:
				break;
		}

		subscription.notification.nextSchedulerScanTime = Calendar.getInstance();
		subscription.notification.nextSchedulerScanTime.add(Calendar.MINUTE, subscription.notification.scheduledRunEvery);
		
		// default values
		subscription.notification.newAdvertsCount = 0;
		subscription.notification.isMinimumAmountAchieved = false;
		subscription.notification.lastNotifiedTime = Calendar.getInstance();
		subscription.notification.nextSchedulerScanTime = Calendar.getInstance();

		
		repository.saveSubscription(subscription);
		
		// Covert to model
		SubscriptionModel responseModel = SubscriptionModel.convertToModel(subscription, repository);
		
		return ResponseWrapper.with(Status.OK, responseModel, "Operation successfully completed");
	}
	
	
	
	
	
	
	
	public ResponseWrapper getSubscriptionAdverts(SubscriptionAdvertsRequestModel model) throws AccessDeniedException, DataValidationException, RecordNotFoundException {
		// Get User
		User user = getUserOrThrowException();
		
		// Check model is null
		model = model == null ? new SubscriptionAdvertsRequestModel() : model;
		
		// Pagination
		int offset = model.offset == null ? 0 : model.offset;
		int limit = (model.limit == null || model.limit < 1) ? 25 : (model.limit > SUBSCRIPTIONS_PAGINATION_MAX_LIMIT ? 25 : model.limit);
		
		Subscription subscription = repository.getSubscription(model.subscriptionId);
		
		if (!user.id.equals(subscription.user.id))
			throw new AccessDeniedException("Subscription doesn't belong to the user!");
		
		// Get Subscriptions
		List<SubscriptionAdvert> adverts = repository.getSubscriptionAdverts(subscription, offset, limit);

		List<SubscriptionAdvertModel> models = SubscriptionAdvertModel.convertToModels(adverts);
		
		// Get counters	
		long totalCount = repository.getSubscriptionAdcertsCount(subscription);
		long page = (offset < limit || limit == 0) ? 1 : (offset / limit) + 1; 
		long pages = (totalCount + limit -1) / limit;
		
		// convert to list model
		DataListWrapper listWrapper = DataListWrapper.with(models, totalCount, page, pages);
		
		return ResponseWrapper.with(Status.OK, listWrapper, "Operation successfully completed");
	}


	
	public ResponseWrapper getSubscriptionAdvert(String subscriptionAdvertId) throws AccessDeniedException, RecordNotFoundException, DataValidationException {
		// Get User
		User user = getUserOrThrowException();
		
		// Get Subscriptions
		SubscriptionAdvert subscriptionAdvert = repository.getSubscriptionAdvert(subscriptionAdvertId);

		if (!user.id.equals(subscriptionAdvert.subscription.user.id))
			throw new AccessDeniedException("Subscription doesn't belong to the user!");
		
		SubscriptionAdvertModel model = SubscriptionAdvertModel.covertToModel(subscriptionAdvert);
		
		return ResponseWrapper.with(Status.OK, model, "Operation successfully completed");
	}
	
}
