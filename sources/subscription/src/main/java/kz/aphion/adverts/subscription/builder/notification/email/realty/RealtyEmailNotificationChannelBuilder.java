package kz.aphion.adverts.subscription.builder.notification.email.realty;

import java.io.IOException;
import java.util.List;

import kz.aphion.adverts.notification.mq.models.NotificationChannel;
import kz.aphion.adverts.persistence.adverts.AdvertOperationType;
import kz.aphion.adverts.persistence.realty.RealtyType;
import kz.aphion.adverts.persistence.subscription.Subscription;
import kz.aphion.adverts.persistence.subscription.SubscriptionAdvert;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.TemplateException;

/**
 * Строитель email уведомлений по объявлениям о недвижимости. 
 * @author artem.demidovich
 *
 * Created at Nov 9, 2017
 */
public class RealtyEmailNotificationChannelBuilder {

	private static Logger logger = LoggerFactory.getLogger(RealtyEmailNotificationChannelBuilder.class);

	public NotificationChannel build(Subscription subscription, List<SubscriptionAdvert> subscriptionAdverts) throws TemplateException, IOException {
		if (RealtyType.FLAT != RealtyType.valueOf(subscription.advertSubType))
			throw new NotImplementedException("Suppported only Flat notifications");
		
		// TODO Проанализировать и сделать 1 шаблон для квартир по продаже и аренде
		if (subscription.operationType == AdvertOperationType.SELL){
			return new FlatSellRealtyEmailNotificationChannelBuilder().build(subscription, subscriptionAdverts);
		}
		
		if (subscription.operationType == AdvertOperationType.RENT){
			return new FlatRentRealtyEmailNotificationChannelBuilder().build(subscription, subscriptionAdverts);
		}
		
		logger.error("Doesn't find RealtyEmailNotificationChannelBuilder for subscription.id " + subscription.id);
		return null;
	}
	
}
