package kz.aphion.adverts.models;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import kz.aphion.adverts.persistence.adverts.Advert;
import kz.aphion.adverts.persistence.adverts.AdvertLocation;
import kz.aphion.adverts.persistence.adverts.AdvertOperationType;
import kz.aphion.adverts.persistence.adverts.AdvertPhoto;
import kz.aphion.adverts.persistence.adverts.AdvertPublisher;
import kz.aphion.adverts.persistence.adverts.AdvertSource;
import kz.aphion.adverts.persistence.adverts.AdvertStatus;
import kz.aphion.adverts.persistence.adverts.AdvertType;

/**
 * Модель generic объявления
 * @author artem.demidovich
 *
 * Created at Dec 12, 2017
 */
public class AdvertModel {

	protected Advert advert;
	
	public AdvertModel(Advert advert) {
		this.advert = advert;
	}
	
	public Advert getAdvert() {
		return advert;
	}

	public AdvertType getType() {
		return advert.type;
	}

	public void setType(AdvertType type) {
		this.advert.type = type;
	}

	public String getSubType() {
		return advert.subType;
	}

	public void setSubType(String subType) {
		this.advert.subType = subType;
	}

	public AdvertOperationType getOperation() {
		return advert.operation;
	}

	public void setOperation(AdvertOperationType operation) {
		this.advert.operation = operation;
	}

	public AdvertLocation getLocation() {
		return advert.location;
	}

	public void setLocation(AdvertLocation location) {
		this.advert.location = location;
	}

	public HashMap<String, Object> getData() {
		if (advert.data == null)
			advert.data = new HashMap<String, Object>();
		return advert.data;
	}

	public void setData(HashMap<String, Object> data) {
		this.advert.data = data;
	}

	public AdvertPublisher getPublisher() {
		return advert.publisher;
	}

	public void setPublisher(AdvertPublisher publisher) {
		this.advert.publisher = publisher;
	}

	public AdvertSource getSource() {
		return advert.source;
	}

	public void setSource(AdvertSource source) {
		this.advert.source = source;
	}

	public Calendar getPublishedAt() {
		return advert.publishedAt;
	}

	public void setPublishedAt(Calendar publishedAt) {
		this.advert.publishedAt = publishedAt;
	}

	public Calendar getExpireAt() {
		return advert.expireAt;
	}

	public void setExpireAt(Calendar expireAt) {
		this.advert.expireAt = expireAt;
	}

	public Calendar getLastUpdatedAt() {
		return advert.lastUpdatedAt;
	}

	public void setLastUpdatedAt(Calendar lastUpdatedAt) {
		this.advert.lastUpdatedAt = lastUpdatedAt;
	}

	public AdvertStatus getStatus() {
		return advert.status;
	}

	public void setStatus(AdvertStatus status) {
		this.advert.status = status;
	}

	public Boolean getHasPhoto() {
		return advert.hasPhoto;
	}

	public void setHasPhoto(Boolean hasPhoto) {
		this.advert.hasPhoto = hasPhoto;
	}

	public List<AdvertPhoto> getPhotos() {
		return advert.photos;
	}

	public void setPhotos(List<AdvertPhoto> photos) {
		this.advert.photos = photos;
	}	
	
}
