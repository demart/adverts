package kz.aphion.adverts.models;

import java.util.Calendar;
import java.util.HashMap;

import kz.aphion.adverts.persistence.adverts.AdvertOperationType;
import kz.aphion.adverts.persistence.adverts.AdvertType;
import kz.aphion.adverts.persistence.subscription.Subscription;
import kz.aphion.adverts.persistence.subscription.SubscriptionStatus;
import kz.aphion.adverts.persistence.subscription.notification.SubscriptionNotification;
import kz.aphion.adverts.persistence.users.User;

/**
 * Модель поверх сущности подписки
 * @author artem.demidovich
 *
 * Created at Dec 12, 2017
 */
public class SubscriptionModel {
	
	protected Subscription subscription;
	
	public SubscriptionModel(Subscription subscription) {
		this.subscription = subscription;
	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public User getUser() {
		return subscription.user;
	}

	public void setUser(User user) {
		subscription.user = user;
	}

	public AdvertType getAdvertType() {
		return subscription.advertType;
	}

	public void setAdvertType(AdvertType advertType) {
		subscription.advertType = advertType;
	}

	public String getAdvertSubType() {
		return subscription.advertSubType;
	}

	public void setAdvertSubType(String advertSubType) {
		subscription.advertSubType = advertSubType;
	}

	public AdvertOperationType getOperationType() {
		return subscription.operationType;
	}

	public void setOperationType(AdvertOperationType operationType) {
		subscription.operationType = operationType;
	}

	public Calendar getStartedAt() {
		return subscription.startedAt;
	}

	public void setStartedAt(Calendar startedAt) {
		subscription.startedAt = startedAt;
	}

	public Calendar getExpiresAt() {
		return subscription.expiresAt;
	}

	public void setExpiresAt(Calendar expiresAt) {
		subscription.expiresAt = expiresAt;
	}

	public SubscriptionNotification getNotification() {
		return subscription.notification;
	}

	public void setNotification(SubscriptionNotification notification) {
		subscription.notification = notification;
	}

	public HashMap<String, Object> getCriteria() {
		if (subscription.criteria == null)
			subscription.criteria = new HashMap<String, Object>();
		return subscription.criteria;
	}

	public void setCriteria(HashMap<String, Object> criteria) {
		subscription.criteria = criteria;
	}

	public SubscriptionStatus getStatus() {
		return subscription.status;
	}

	public void setStatus(SubscriptionStatus status) {
		subscription.status = status;
	}
	
}
