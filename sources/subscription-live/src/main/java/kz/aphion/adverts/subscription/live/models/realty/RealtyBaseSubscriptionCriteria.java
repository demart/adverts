package kz.aphion.adverts.subscription.live.models.realty;

import java.util.List;

import kz.aphion.adverts.persistence.Region;
import kz.aphion.adverts.persistence.realty.Realty;
import kz.aphion.adverts.persistence.realty.RealtyPublisherType;
import kz.aphion.adverts.persistence.realty.types.RealtyOperationType;
import kz.aphion.adverts.persistence.realty.types.RealtyType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RealtyBaseSubscriptionCriteria {
	
	private static Logger logger = LoggerFactory.getLogger(RealtyBaseSubscriptionCriteria.class);
	
	/**
	 * Вид недвижимости
	 */
	public RealtyType type;
	
	/**
	 * Тип операции
	 */
	public RealtyOperationType operation;
	
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
	public List<RealtyPublisherType> publisherTypes;
	
	/**
	 * Где ищем объявление
	 */
	public List<String> regions;
	
	
	public boolean isAdvertBelongsToQuery(Realty advert) {
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
	
	public boolean checkRegions(Realty advert) {
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
	
	public boolean checkPublisherTypes(Realty advert) {
		if (publisherTypes != null) {
			if (advert.publisher == null || advert.publisher.publisherType == null) {
				logger.warn("Found realty advert [{}] without publisher informaiton! Fix issue!", advert.id);
				return false;
			}
			
			for (RealtyPublisherType realtyPublisherType : publisherTypes) {
				if (realtyPublisherType.equals(advert.publisher.publisherType))
					return true;
			}
			
			return false;
		}
		
		return true;
	}
	
	public boolean checkPrice(Realty advert) {
		if (priceFrom != null && priceFrom > advert.price) {
			return false;
		}
		
		if (priceTo != null && priceTo < advert.price) {
				return false;
		}
		
		return true;
	}
	
	public boolean checkPhoto(Realty advert) {
		if (hasPhoto != null) {
			if (advert.hasPhoto == hasPhoto) {
				return true;
			} else {
				return false;
			}
		} 
		return true;
	}
	
	public boolean checkOperationType(Realty advert) {
		if (operation != null && operation != advert.operation)
			return false;
		return true;
	}
	
	
	public boolean checkType(Realty advert) {
		if (type != null && type != advert.type)
			return false;
		return true;
	}
	
	
}
