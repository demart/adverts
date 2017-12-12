package kz.aphion.adverts.subscription.live.models.realty;

import java.util.List;

import kz.aphion.adverts.persistence.Region;
import kz.aphion.adverts.persistence.adverts.Advert;
import kz.aphion.adverts.persistence.adverts.AdvertOperationType;
import kz.aphion.adverts.persistence.adverts.AdvertPublisherType;
import kz.aphion.adverts.persistence.realty.RealtyType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RealtyBaseSubscriptionCriteria {
	
	private static Logger logger = LoggerFactory.getLogger(RealtyBaseSubscriptionCriteria.class);
	
	/**
	 * Вид недвижимости
	 */
	public RealtyType subType;
	
	/**
	 * Тип операции
	 */
	public AdvertOperationType operation;
	
	/**
	 * Цена от
	 */
	public Long priceFrom;
	
	/**
	 * Цена до
	 */
	public Long priceTo;
	
	/**
	 * Нужно ли фото
	 */
	public Boolean hasPhoto;
	
	/**
	 * Кто опубликовал объявление (различные виды)
	 */
	public List<AdvertPublisherType> publisherTypes;
	
	/**
	 * Где ищем объявление
	 */
	public List<String> regions;
	
	
	public boolean isAdvertBelongsToQuery(Advert advert) {
		if (checkType(advert) == false)
			return false;
		if (checkOperationType(advert) == false)
			return false;
		if (checkPhoto(advert) == false)
			return false;
		if (checkPrice(advert) == false)
			return false;
		if (checkPublisherTypes(advert) == false)
			return false;
		if (checkRegions(advert) == false)
			return false;
		
		return true;
	}
	
	
	// Массив параметров,
	// Превращать в критерии
	// Каждое объявление проверять по критериям
	
	public boolean checkRegions(Advert advert) {
		if (regions != null) {
			if (advert.location.regions == null) {
				logger.warn("Found realty advert [{}] without regions informaiton! Fix issue!", advert.id);
				return false;
			}
			
			for (Region region : advert.location.regions) {
				for (String regionCode : regions) {
					if (regionCode.equals(region.code))
						return true;
				}
			}
			
			return false;
			
		}
		
		return true;
	}
	
	public boolean checkPublisherTypes(Advert advert) {
		if (publisherTypes != null) {
			if (advert.publisher == null || advert.publisher.type == null) {
				logger.warn("Found realty advert [{}] without publisher informaiton! Fix issue!", advert.id);
				return false;
			}
			
			for (AdvertPublisherType realtyPublisherType : publisherTypes) {
				if (realtyPublisherType.equals(advert.publisher.type))
					return true;
			}
			
			return false;
		}
		
		return true;
	}
	
	public boolean checkPrice(Advert advert) {
		if (priceFrom != null && priceFrom > advert.price) {
			return false;
		}
		
		if (priceTo != null && priceTo < advert.price) {
				return false;
		}
		
		return true;
	}
	
	public boolean checkPhoto(Advert advert) {
		if (hasPhoto != null) {
			if (advert.hasPhoto == hasPhoto) {
				return true;
			} else {
				return false;
			}
		} 
		return true;
	}
	
	public boolean checkOperationType(Advert advert) {
		if (operation != null && operation != advert.operation)
			return false;
		return true;
	}
	
	
	public boolean checkType(Advert advert) {
		if (subType != null && subType != RealtyType.valueOf(advert.subType))
			return false;
		return true;
	}
	
	
}
