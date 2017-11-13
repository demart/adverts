package kz.aphion.adverts.subscription.builder.notification.email;

import java.io.IOException;
import java.util.List;

import kz.aphion.adverts.notification.mq.models.NotificationChannel;
import kz.aphion.adverts.persistence.subscription.Subscription;
import kz.aphion.adverts.persistence.subscription.SubscriptionAdvert;
import kz.aphion.adverts.subscription.builder.notification.email.realty.RealtyEmailNotificationChannelBuilder;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.TemplateException;

/**
 * Строитель корректных email сообщений
 * 
 * @author artem.demidovich
 *
 * Created at Oct 25, 2017
 */
public class EmailNotificationChannelBuilder {

	private static Logger logger = LoggerFactory.getLogger(EmailNotificationChannelBuilder.class);
	
	public NotificationChannel build(Subscription subscription, List<SubscriptionAdvert> subscriptionAdverts) throws TemplateException, IOException {

		if (subscription.user == null) {
			logger.error("Subscription.User is Null, subscription.id {}", subscription.id);
			return null;
		}
			
		if (StringUtils.isBlank(subscription.user.email)) { 
			// TODO Add email validation, but probably system should even avoid to store incorrect email
			logger.error("Subscription.User.Email is empty, subscription.id {}", subscription.id);
			return null;
		}
		
		
		switch (subscription.advertType) {
			case REALTY:
				return new RealtyEmailNotificationChannelBuilder().build(subscription, subscriptionAdverts);
			
			case AUTO:
				logger.warn("AdvertType: AUTO doesn't supported. Please check why it may happened");
				break;
			case PRODUCTS:
				logger.warn("AdvertType: PRODUCTS doesn't supported. Please check why it may happened");
				break;
			case SERVICE:
				logger.warn("AdvertType: SERVICE doesn't supported. Please check why it may happened");
				break;
			default:
				logger.warn("AdvertType: Default doesn't supported. Please check why it may happened");
				break;
		}
		
		logger.error("EmailNotificationChannelBuilder not found for subscription.id {}", subscription.id);
		return null;
	}

}
