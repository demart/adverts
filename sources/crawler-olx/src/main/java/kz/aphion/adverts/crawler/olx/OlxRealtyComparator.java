package kz.aphion.adverts.crawler.olx;

import kz.aphion.adverts.common.utils.ValuesUtils;
import kz.aphion.adverts.persistence.adverts.Advert;
import kz.aphion.adverts.persistence.adverts.AdvertOperationType;
import kz.aphion.adverts.persistence.realty.RealtyType;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс позволяет справнивать 2 объекта недвижимости на наличие различий или идентичности
 * 
 * @author artem.demidovich
 *
 * Created at May 20, 2016
 */
public class OlxRealtyComparator {

	private static Logger logger = LoggerFactory.getLogger(OlxRealtyComparator.class);

	/**
	 * Сравнивает две версии объектов и в случае если новая версия является более актульной возвращает true в противном случае false.
	 * Дополнительно методв выводит разницу если таковая имеется
	 * @param old
	 * @return
	 */
	public static Boolean isRealtyUpdated(Advert oldRealty, Advert newRealty) {
		if (oldRealty.subType.equalsIgnoreCase(RealtyType.FLAT.toString()) && oldRealty.operation == AdvertOperationType.SELL)
			return isFlatSellRealtyUpdated(oldRealty, newRealty);

		if (oldRealty.subType.equalsIgnoreCase(RealtyType.FLAT.toString()) && oldRealty.operation == AdvertOperationType.RENT)
			return isFlatRentRealtyUpdated(oldRealty, newRealty);


		throw new NotImplementedException("Comparator for [" + oldRealty.type + "] type not implemented");
	}
	
	public static Boolean isFlatSellRealtyUpdated(Advert oldRealty, Advert newRealty) {
		Boolean isUpdated = false;
		
		if (oldRealty.publishedAt.compareTo(newRealty.publishedAt) < 0) {
			isUpdated = true;
			logger.info("Advert [{}] changed publishedAt from [{}] to [{}]", oldRealty.source.externalId, oldRealty.publishedAt.getTime().toString(), newRealty.publishedAt.getTime().toString());
		} else {
			if (oldRealty.publishedAt.compareTo(newRealty.publishedAt) == 0) {
				// Date is equal so we need to check data changes
				
				isUpdated = isFlatCommonFieldsUpdated(oldRealty, newRealty);
				
			} else {
				// new Advert is older so there is no any changes
			}
		}
		
		return isUpdated;
	}
	
	public static Boolean isFlatRentRealtyUpdated(Advert oldRealty, Advert newRealty) {
		Boolean isUpdated = false;
		
		if (oldRealty.publishedAt.compareTo(newRealty.publishedAt) < 0) {
			isUpdated = true;
			logger.info("Advert [{}] changed publishedAt from [{}] to [{}]", oldRealty.source.externalId, oldRealty.publishedAt.getTime().toString(), newRealty.publishedAt.getTime().toString());
		} else {
			if (oldRealty.publishedAt.compareTo(newRealty.publishedAt) == 0) {
				// Date is equal so we need to check data changes
				if (oldRealty.data.get("rentPeriod") != newRealty.data.get("rentPeriod")) {
					isUpdated = true;
					logger.info("Advert [{}] rentOption changed from [{}] to [{}]", oldRealty.source.externalId, oldRealty.data.get("rentPeriod").toString(), newRealty.data.get("rentPeriod").toString());
				}
				
				isUpdated = isFlatCommonFieldsUpdated(oldRealty, newRealty);
				
			} else {
				// new Advert is older so there is no any changes
			}
		}
		
		return isUpdated;
	}

	
	public static Boolean isFlatCommonFieldsUpdated(Advert oldRealty, Advert newRealty) {
		Boolean isUpdated = false;
		
		Long oldPrice = convertObjectoToLong(oldRealty.data.get("price"));
		Long newPrice = convertObjectoToLong(newRealty.data.get("price"));
		if (!ValuesUtils.isLongsEqual(oldPrice, newPrice)) {
			isUpdated = true;
			logger.info("Advert [{}] price changed from [{}] to [{}]", oldRealty.source.externalId, oldPrice, newPrice);
		}
		
		if (!oldRealty.location.region.code.equals(newRealty.location.region.code)) {
			isUpdated = true;
			logger.info("Advert [{}] region changed from [{}] to [{}]", oldRealty.source.externalId, oldRealty.location.region.code.toString(), newRealty.location.region.code.toString());
		}
		
		Long oldFlatFloor = convertObjectoToLong(oldRealty.data.get("flatFloor"));
		Long newFlatFloor = convertObjectoToLong(newRealty.data.get("flatFloor"));
		if (!ValuesUtils.isLongsEqual(oldFlatFloor, newFlatFloor)) {
			isUpdated = true;
			logger.info("Advert [{}] flatFloor changed from [{}] to [{}]", oldRealty.source.externalId, oldFlatFloor, newFlatFloor);
		}
		
		Float oldRooms = convertObjectoToFloat(oldRealty.data.get("rooms"));
		Float newRooms = convertObjectoToFloat(newRealty.data.get("rooms"));
		if (!ValuesUtils.isFloatsEqual(oldRooms, newRooms)) {
			isUpdated = true;
			logger.info("Advert [{}] rooms changed from [{}] to [{}]", oldRealty.source.externalId, oldRooms, newRooms);
		}
		
		
		Long oldHouseFloorCount = convertObjectoToLong(oldRealty.data.get("houseFloorCount"));
		Long newHouseFloorCount = convertObjectoToLong(newRealty.data.get("houseFloorCount"));
		if (!ValuesUtils.isLongsEqual(oldHouseFloorCount, newHouseFloorCount)) {
			isUpdated = true;
			logger.info("Advert [{}] houseFloorCount changed from [{}] to [{}]", oldRealty.source.externalId, oldHouseFloorCount, newHouseFloorCount);
		}
		
		
		Float oldSquare = convertObjectoToFloat(oldRealty.data.get("square"));
		Float newSquare = convertObjectoToFloat(newRealty.data.get("square"));
		if (!ValuesUtils.isFloatsEqual(oldSquare, newSquare)) {
			isUpdated = true;
			logger.info("Advert [{}] square changed from [{}] to [{}]", oldRealty.source.externalId, oldSquare, newSquare);
		}
		
		Float oldSquareLiving = convertObjectoToFloat(oldRealty.data.get("squareLiving"));
		Float newSquareLiving = convertObjectoToFloat(newRealty.data.get("squareLiving"));
		if (!ValuesUtils.isFloatsEqual(oldSquareLiving, newSquareLiving)) {
			isUpdated = true;
			logger.info("Advert [{}] squareLiving changed from [{}] to [{}]", oldRealty.source.externalId, oldSquareLiving, newSquareLiving);
		}
		
		Float oldSquareKitchen = convertObjectoToFloat(oldRealty.data.get("squareKitchen"));
		Float newSquareKitchen = convertObjectoToFloat(newRealty.data.get("squareKitchen"));
		if (!ValuesUtils.isFloatsEqual(oldSquareKitchen, newSquareKitchen)) {
			isUpdated = true;
			logger.info("Advert [{}] squareKitchen changed from [{}] to [{}]", oldRealty.source.externalId, oldSquareKitchen, newSquareKitchen);
		}
		
		String oldText = String.valueOf(oldRealty.data.get("text"));
		String newText = String.valueOf(newRealty.data.get("text"));
		if (!ValuesUtils.isStringsEqual(oldText, newText)) {
			isUpdated = true;
			logger.info("Advert [{}] squareKitchen changed from [{}] to [{}]", oldRealty.source.externalId, oldText, newText);
		}
		
		// If publishedAt date the same
		if (!ValuesUtils.isBooleansEqual(oldRealty.hasPhoto,newRealty.hasPhoto)) {
			isUpdated = true;
			logger.info("Advert [{}] hasPhoto changed from [" + oldRealty.hasPhoto + "] to [" + newRealty.hasPhoto + "]", oldRealty.source.externalId);
		}
		
		// If publishedAt date the same
		if (oldRealty.photos != null && newRealty.photos != null) {
			if (oldRealty.photos.size() != newRealty.photos.size()) {
				isUpdated = true;
				logger.info("Advert [{}] photos size changed from [" + oldRealty.photos.size() + "] to [" + newRealty.photos.size() + "]", oldRealty.source.externalId);
			}
		}
		
		if (oldRealty.publisher.phones.size() != newRealty.publisher.phones.size()) {
			isUpdated = true;
			logger.info("Advert [{}] contact phones size changed from [" + oldRealty.publisher.phones.size() + "] to [" + newRealty.publisher.phones.size() + "]", oldRealty.source.externalId);
		}
		
		return isUpdated;
	}
	
	
	private static Float convertObjectoToFloat(Object obj) {
		String strVal = String.valueOf(obj);
		if ("null".equals(strVal))
			return null;
		return Float.valueOf(strVal);
	}
	
	private static Long convertObjectoToLong(Object obj) {
		String strVal = String.valueOf(obj);
		if ("null".equals(strVal))
			return null;
		return Long.valueOf(strVal);
	}
	
}
