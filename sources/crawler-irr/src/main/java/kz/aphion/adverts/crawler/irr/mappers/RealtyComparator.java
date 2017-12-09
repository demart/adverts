package kz.aphion.adverts.crawler.irr.mappers;

import kz.aphion.adverts.persistence.adverts.Advert;
import kz.aphion.adverts.persistence.adverts.AdvertPhoto;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatRentPeriodType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RealtyComparator {

	private static Logger logger = LoggerFactory.getLogger(RealtyComparator.class);

	/**
	 * Проверяет есть ли изменения в лучшую сторону между существующим объявлением и полученным
	 * @param oldRealty
	 * @param newRealty
	 * @return
	 */
	public static boolean isUpdatedFlatSellRealty(Advert oldRealty, Advert newRealty) {
		// Остальные проверки 
		return isFlatCommonFieldsUpdated(oldRealty, newRealty);
	}
	
	/**
	 * Проверяем на обновление объявление в категории аренда квартир
	 * @param oldRealty
	 * @param newRealty
	 * @return
	 */
	public static boolean isUpdatedFlatRentRealty(Advert oldRealty, Advert newRealty) {
		//Проверяем срок аренды
		FlatRentPeriodType oldType = FlatRentPeriodType.valueOf(String.valueOf(oldRealty.data.get("rentPeriod")));
		FlatRentPeriodType newType = FlatRentPeriodType.valueOf(String.valueOf(newRealty.data.get("rentPeriod")));
		if (oldType != newType) {
			logger.info("Advert [{}], rent period changed from [{}] to [{}]", oldRealty.source.externalId, oldType, newType);
			return true;
		}
		
		// Остальные проверки 
		return isFlatCommonFieldsUpdated(oldRealty, newRealty);
	}
	
	
	public static boolean isFlatCommonFieldsUpdated(Advert oldRealty, Advert newRealty) {
		String oldText = String.valueOf(oldRealty.data.get("text"));
		String newText = String.valueOf(newRealty.data.get("text"));
		if (!isEqualsTexts(oldText, newText)) {
			logger.info("Advert [{}] text changed from [{}] to [{}]", oldRealty.source.externalId, oldText, newText);
			return true;
		}
		
		//Проверяем осталась ли прежняя цена
		//Бывает что люди не указывают цену. Поэтому надо проверять указана ли она
		if (!isEqualsLongs(oldRealty.price, newRealty.price)) {
			logger.info("Advert [{}] price changed from [{}] to [{}]", oldRealty.source.externalId, oldRealty.price, newRealty.price);
			return true;
		}
				
		//Проверяем дату публикации. Если изменилась дата, то соответственно перезапишем объявление,
		//так как это показатель того. что оно еще актуально.
		if (oldRealty.publishedAt.getTimeInMillis() - newRealty.publishedAt.getTimeInMillis() < 0) {
			logger.info("Advert [{}] published date changed from [{}] to [{}]", oldRealty.source.externalId, oldRealty.publishedAt.getTime().toLocaleString(), newRealty.publishedAt.getTime().toLocaleString());
			return true;
		}
		
		//Проверяем количество комнат
		Float oldRooms = convertObjectoToFloat(oldRealty.data.get("rooms"));
		Float newRooms = convertObjectoToFloat(newRealty.data.get("rooms"));
		if (!isEqualsFloats(oldRooms, newRooms)) {
			logger.info("Advert [{}] rooms changed from [{}] to [{}]", oldRealty.source.externalId, oldRealty, newRooms);
			return true;
		}
		
		Float oldSquare = convertObjectoToFloat(oldRealty.data.get("square"));
		Float newSquare = convertObjectoToFloat(newRealty.data.get("square"));
		if (!isEqualsFloats(oldSquare, newSquare)) {
			logger.info("Advert [{}] square changed from [{}] to [{}]", oldRealty.source.externalId, oldSquare, newSquare);
			return true;
		}
		
		//Проверяем год постройки
		Long oldHourseYear = convertObjectoToLong(oldRealty.data.get("houseYear"));
		Long newHourseYear = convertObjectoToLong(newRealty.data.get("houseYear"));
		if (!isEqualsLongs(oldHourseYear, newHourseYear)) {
			logger.info("Advert [{}] houseYear changed from [{}] to [{}]", oldRealty.source.externalId, oldHourseYear, newHourseYear);
			return true;
		}
	
		//Проверяем  жилую площадь
		Float oldSquareLiving = convertObjectoToFloat(oldRealty.data.get("squareLiving"));
		Float newSquareLiving = convertObjectoToFloat(newRealty.data.get("squareLiving"));
		if (!isEqualsFloats(oldSquareLiving, newSquareLiving)) {
			logger.info("Advert [{}] living square changed from [{}] to [{}]", oldRealty.source.externalId, oldSquareLiving, newSquareLiving);
			return true;
		}
		
		//Проверяем  площадь кухни
		Float oldSquareKitchen = convertObjectoToFloat(oldRealty.data.get("squareKitchen"));
		Float newSquareKitchen = convertObjectoToFloat(newRealty.data.get("squareKitchen"));
		if (!isEqualsFloats(oldSquareKitchen, newSquareKitchen)) {
			logger.info("Advert [{}] kitchen square changed from [{}] to [{}]", oldRealty.source.externalId, oldSquareKitchen, newSquareKitchen);
			return true;
		}
		
		//Проверяем  высоту потолков
		Float oldCeilingHeight = convertObjectoToFloat(oldRealty.data.get("ceilingHeight"));
		Float newCeilingHeight = convertObjectoToFloat(newRealty.data.get("ceilingHeight"));
		if (!isEqualsFloats(oldCeilingHeight, newCeilingHeight)) {
			logger.info("Advert [{}] ceiling height changed from [{}] to [{}]", oldRealty.source.externalId, oldCeilingHeight, newCeilingHeight);
			return true;
		}
		
		// Фотографии редактируются отдельно и проходят проверку. 
		// Нужно сравнивать были ли изменения даже в случае если кол-во одинаковое 
		if ((oldRealty.photos != null && newRealty.photos == null) ||
				(oldRealty.photos == null && newRealty.photos != null)) {
			return true;
		}
		
		if (oldRealty.photos != null && newRealty.photos != null) {
			if (oldRealty.photos.size() != newRealty.photos.size()) {
				logger.info("Advert [{}] photos size changed from [{}] to [{}]", oldRealty.source.externalId, oldRealty.photos.size(), newRealty.photos.size());
				return true;
			}
			
			for (AdvertPhoto oldPhoto : oldRealty.photos) {
				boolean wasFound = false;
				for (AdvertPhoto newPhoto : newRealty.photos) {
					if (newPhoto.path.equalsIgnoreCase(oldPhoto.path))
						wasFound = true;
				}
				// Если нашли разницу
				if (wasFound == false) {
					logger.info("Advert [{}] photos path changed", oldRealty.source.externalId);
					return true;
				}
			}
			
		}
		
		return false;		
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
	
	
	/**
	 * Метод корректно сравнивает значения Float учитывая null и другие варианты
	 * @param oldValue
	 * @param newValue
	 * @return
	 */
	private static Boolean isEqualsFloats(Float oldValue, Float newValue) {
		if (oldValue == null && newValue == null)
			return true;
		if ((oldValue == null && newValue != null) || (oldValue != null && newValue == null))
			return false;
		
		if (Math.abs(oldValue - newValue) < 0.0001)
			return true;
		
		return false;
	}
	
	/**
	 * Сравнивает два текстовых поля, при этом учитывая null и выполняя для каждого текста trim
	 * @param oldValue
	 * @param newValue
	 * @return
	 */
	private static Boolean isEqualsTexts(String oldValue, String newValue) {
		if (oldValue == null && newValue == null)
			return true;
		if ((oldValue == null && newValue != null) || (oldValue != null && newValue == null))
			return false;
		
		return oldValue.trim().equals(newValue.trim());
	}
	
	/**
	 * Сравнивает два Boolean при этом учитывает NULL значения
	 * @param oldValue
	 * @param newValue
	 * @return
	 */
	private static Boolean isEqualsBoolean(Boolean oldValue, Boolean newValue) {
		if (oldValue == null && newValue == null)
			return true;
		if ((oldValue == null && newValue != null) || (oldValue != null && newValue == null))
			return false;
		return oldValue == newValue;
	}
	
	/**
	 * Сравнивает два Long значение при этом учитывает NULL значения
	 * @param oldValue
	 * @param newValue
	 * @return
	 */
	private static Boolean isEqualsLongs(Long oldValue, Long newValue) {
		if (oldValue == null && newValue == null)
			return true;
		if ((oldValue == null && newValue != null) || (oldValue != null && newValue == null))
			return false;
		return oldValue.equals(newValue);
	}
	
	
	
}
