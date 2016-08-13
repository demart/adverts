package kz.aphion.adverts.subscription;

import java.util.Calendar;

import kz.aphion.adverts.persistence.realty.types.RealtyOperationType;
import kz.aphion.adverts.persistence.realty.types.RealtyType;
import kz.aphion.adverts.persistence.subscription.Subscription;
import kz.aphion.adverts.persistence.subscription.SubscriptionAdvertType;
import kz.aphion.adverts.persistence.subscription.SubscriptionStatus;
import kz.aphion.adverts.persistence.subscription.criteria.KeywordsCriteriaType;
import kz.aphion.adverts.persistence.subscription.criteria.realty.RealtySellFlatSubscriptionCriteria;
import kz.aphion.adverts.persistence.subscription.notification.SubscriptionNotification;
import kz.aphion.adverts.persistence.subscription.notification.SubscriptionNotificationScheduledType;
import kz.aphion.adverts.persistence.subscription.notification.SubscriptionNotificationType;
import kz.aphion.adverts.subscription.providers.MongoDbProvider;

import org.mongodb.morphia.Datastore;

public class CreateTestSubscriptions {

	
	public static void createTestSubscription() throws Exception {
		//createSellTest();
		//createRentTest();
	}
	
	private static void createSellTest() throws Exception {
		Datastore ds = MongoDbProvider.getInstance().getDatastore();
		
		
		Subscription subscription = new Subscription();
		
		subscription.advertType = SubscriptionAdvertType.REALTY;
		subscription.created = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 12);
		subscription.expiresAt = cal;
		
		subscription.startedAt = Calendar.getInstance();
		subscription.updated = Calendar.getInstance();
		
		subscription.status = SubscriptionStatus.ACTIVE;
		
		RealtySellFlatSubscriptionCriteria criteria =  new RealtySellFlatSubscriptionCriteria();
		subscription.criteria = criteria;
		
		criteria.operation = RealtyOperationType.SELL;
		criteria.type = RealtyType.FLAT;
		
		criteria.priceFrom = 100l;
		criteria.priceTo = 15000000l;
		
		criteria.roomFrom = 1f;
		criteria.roomTo = 3f;
		
		criteria.ceilingHeightFrom = 1f;
		criteria.ceilingHeightTo = 3f;
		
		criteria.flatFloorFrom = 0l;
		criteria.flatFloorTo = 6l;
		
		criteria.houseFloorCountFrom = 0l;
		criteria.houseFloorCountTo = 10l;
		
		criteria.houseYearFrom = 2000l;
		criteria.houseYearTo = 2010l;
		
		criteria.squareFrom = 0f;
		criteria.squareKitchenFrom = 0f;
		criteria.squareKitchenTo = null;
		criteria.squareLivingFrom = 0f;
		criteria.squareTo = 100f;
		
		criteria.location = null; //
		criteria.balconyGlazingTypes = null;
		criteria.balconyTypes = null;
		criteria.doorTypes = null;
		criteria.flatBuildingTypes = null;
		criteria.floorTypes = null;
		criteria.furnitureTypes = null;
		criteria.hasPhoto = true;
		criteria.internetTypes = null;
		criteria.keywords = null;
		criteria.keywordsType = KeywordsCriteriaType.AND;
		criteria.lavatoryTypes = null;
		criteria.miscellaneous = null;
		criteria.mortgageStatuses = null;
		criteria.parkingTypes = null;
		criteria.phoneTypes = null;

		criteria.privatizedDormTypes = null;
		criteria.publisherTypes = null;
		criteria.renovationTypes = null;
		criteria.residentalComplexs = null;
		criteria.securityTypes = null;
		
		
		subscription.notification = new SubscriptionNotification();
		subscription.notification.type = SubscriptionNotificationType.IMMEDIATE;
		subscription.notification.channels = null;
		subscription.notification.doNotDisturbAtNight = false;
		subscription.notification.lastNotifiedTime = Calendar.getInstance();
		subscription.notification.scheduledRunAfter = 5;
		subscription.notification.scheduledRunEvery = 5;
		subscription.notification.scheduledType = SubscriptionNotificationScheduledType.TIME_AND_RECORDS;

		ds.save(subscription);
	}
	
	private static void createRentTest() throws Exception {
		Datastore ds = MongoDbProvider.getInstance().getDatastore();
		
		
		Subscription subscription = new Subscription();
		
		subscription.advertType = SubscriptionAdvertType.REALTY;
		subscription.created = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 12);
		subscription.expiresAt = cal;
		
		subscription.startedAt = Calendar.getInstance();
		subscription.updated = Calendar.getInstance();
		
		subscription.status = SubscriptionStatus.ACTIVE;
		
		RealtySellFlatSubscriptionCriteria criteria =  new RealtySellFlatSubscriptionCriteria();
		subscription.criteria = criteria;
		
		criteria.operation = RealtyOperationType.RENT;
		criteria.type = RealtyType.FLAT;
		
		criteria.priceFrom = null;
		criteria.priceTo = 15000000l;
		
		//criteria.roomFrom = 1f;
		//criteria.roomTo = 5f;
		
		//criteria.ceilingHeightFrom = 1f;
		//criteria.ceilingHeightTo = 5f;
		
		//criteria.flatFloorFrom = 0l;
		//criteria.flatFloorTo = 16l;
		
		//criteria.houseFloorCountFrom = 0l;
		//criteria.houseFloorCountTo = 100l;
		
		criteria.houseYearFrom = null;
		criteria.houseYearTo = null;
		
		criteria.squareFrom = null;
		criteria.squareTo = null;
		
		//criteria.squareKitchenFrom = 0f;
		criteria.squareKitchenTo = null;
		//criteria.squareLivingFrom = 0f;
		criteria.squareLivingTo = null;
		
		
		criteria.location = null; //
		criteria.balconyGlazingTypes = null;
		criteria.balconyTypes = null;
		criteria.doorTypes = null;
		criteria.flatBuildingTypes = null;
		criteria.floorTypes = null;
		criteria.furnitureTypes = null;
		criteria.hasPhoto = null;
		criteria.internetTypes = null;
		criteria.keywords = null;
		criteria.keywordsType = KeywordsCriteriaType.AND;
		criteria.lavatoryTypes = null;
		criteria.miscellaneous = null;
		criteria.mortgageStatuses = null;
		criteria.parkingTypes = null;
		criteria.phoneTypes = null;

		criteria.privatizedDormTypes = null;
		criteria.publisherTypes = null;
		criteria.renovationTypes = null;
		criteria.residentalComplexs = null;
		criteria.securityTypes = null;
		
		
		subscription.notification = new SubscriptionNotification();
		subscription.notification.type = SubscriptionNotificationType.IMMEDIATE;
		subscription.notification.channels = null;
		subscription.notification.doNotDisturbAtNight = false;
		subscription.notification.lastNotifiedTime = Calendar.getInstance();
		subscription.notification.scheduledRunAfter = 5;
		subscription.notification.scheduledRunEvery = 5;
		subscription.notification.scheduledType = SubscriptionNotificationScheduledType.TIME_AND_RECORDS;

		ds.save(subscription);
	}
}
