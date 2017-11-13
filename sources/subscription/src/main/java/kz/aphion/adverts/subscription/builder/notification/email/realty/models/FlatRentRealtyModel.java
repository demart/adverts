package kz.aphion.adverts.subscription.builder.notification.email.realty.models;

import kz.aphion.adverts.persistence.subscription.SubscriptionAdvert;

/**
 * Модель данных для формирования элемента объявления в рассылке почтовых уведомлений
 * 
 * @author artem.demidovich
 *
 * Created at Nov 9, 2017
 */
public class FlatRentRealtyModel {

	public String title;
	
	public long price;
	
	public String priceText;
	
	
	public boolean hasImage;
	
	public String imageUrl;
	
	
	public String shortDescription;
	
	public String description;
	
	public String advertUrl;
	
	public SubscriptionAdvert subscriptionAdvert;

	public String publisher;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getPriceText() {
		return priceText;
	}

	public void setPriceText(String priceText) {
		this.priceText = priceText;
	}

	public boolean isHasImage() {
		return hasImage;
	}

	public void setHasImage(boolean hasImage) {
		this.hasImage = hasImage;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAdvertUrl() {
		return advertUrl;
	}

	public void setAdvertUrl(String advertUrl) {
		this.advertUrl = advertUrl;
	}

	public SubscriptionAdvert getSubscriptionAdvert() {
		return subscriptionAdvert;
	}

	public void setSubscriptionAdvert(SubscriptionAdvert subscriptionAdvert) {
		this.subscriptionAdvert = subscriptionAdvert;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

}
