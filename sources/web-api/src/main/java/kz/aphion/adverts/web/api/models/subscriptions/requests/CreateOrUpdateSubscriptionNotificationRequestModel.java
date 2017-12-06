package kz.aphion.adverts.web.api.models.subscriptions.requests;

import java.util.List;

import kz.aphion.adverts.persistence.subscription.notification.SubscriptionNotificationChannelType;
import kz.aphion.adverts.persistence.subscription.notification.SubscriptionNotificationScheduledType;
import kz.aphion.adverts.persistence.subscription.notification.SubscriptionNotificationType;
import kz.aphion.adverts.web.api.exceptions.ModelValidationException;

public class CreateOrUpdateSubscriptionNotificationRequestModel {

	public SubscriptionNotificationType type;
	
	public Boolean doNotDisturbAtNight;

	public SubscriptionNotificationScheduledType scheduledType;
	
	/**
	 * MIN 1 : MAX - 60M * 24H * 31D
	 */
	public Integer scheduledRunEvery;

	/**
	 * MIN 1 : MAX 100
	 */
	public Integer scheduledRunAfter;
	
	public List<SubscriptionNotificationChannelType> channels;
	
	
	
	
	public static void validate(CreateOrUpdateSubscriptionNotificationRequestModel model)  throws ModelValidationException {
		if (model == null)
			throw new ModelValidationException("subscription.notificaion model is null");
		model.validate();
	}
	
	public void validate()  throws ModelValidationException {
		if (type == null)
			throw new ModelValidationException("subscription.notification.type is null");
		
		if (doNotDisturbAtNight == null)
			throw new ModelValidationException("subscription.notification.doNotDisturbAtNight is null");
		
		if (scheduledType == null)
			throw new ModelValidationException("subscription.notification.scheduledType is null");
		
		switch (scheduledType) {
		
			case RECORDS:
				if (scheduledRunAfter == null)
					throw new ModelValidationException("subscription.notification.scheduledRunAfter is null");
				if (scheduledRunAfter < 1 || scheduledRunAfter > 100)
					throw new ModelValidationException("subscription.notification.scheduledRunAfter is invalid");
				break;
	
			case TIME:
				if (scheduledRunEvery == null)
					throw new ModelValidationException("subscription.notification.scheduledRunEvery is null");
				if (scheduledRunEvery < 1 || scheduledRunEvery > (60 * 24 * 31))
					throw new ModelValidationException("subscription.notification.scheduledRunEvery is invalid");
				break;
				
			case TIME_AND_RECORDS:
				// scheduledRunAfter
				if (scheduledRunAfter == null)
					throw new ModelValidationException("subscription.notification.scheduledRunAfter is null");
				if (scheduledRunAfter < 1 || scheduledRunAfter > 100)
					throw new ModelValidationException("subscription.notification.scheduledRunAfter is invalid");
				// scheduledRunEvery
				if (scheduledRunEvery == null)
					throw new ModelValidationException("subscription.notification.scheduledRunEvery is null");
				if (scheduledRunEvery < 1 || scheduledRunEvery > (60 * 24 * 31))
					throw new ModelValidationException("subscription.notification.scheduledRunEvery is invalid");
				break;
				
			default:
				break;
		}
		
		if (channels == null || channels.size() < 1)
			throw new ModelValidationException("subscription.notification.channels is null");

	}
	
	
}
