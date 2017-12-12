package kz.aphion.adverts.crawler.kn.mappers;


import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.common.utils.ValuesUtils;
import kz.aphion.adverts.persistence.CalendarConverter;
import kz.aphion.adverts.persistence.adverts.Advert;
import kz.aphion.adverts.persistence.adverts.AdvertPhoto;
import kz.aphion.adverts.persistence.realty.ResidentialComplex;

import org.mongodb.morphia.mapping.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DBObject;

/**
 * Класс сравнивает 2 версии одинаковых объявлений для того, чтобы найти отличия
 *  
 * @author artem.demidovich
 *
 */
public class RealtyComparator {

	private static Logger logger = LoggerFactory.getLogger(RealtyComparator.class);
	
	/**
	 * Проверяет есть ли изменения в лучшую сторону между существующим объявлением и полученным
	 * из источника
	 * @param advertType 
	 * 
	 * @param existing Существующее объявление
	 * @param updated Новое объявление полученное из источника
	 * @return
	 */
	public static boolean isUpdatedFlatSellRealty(Advert oldRealty, Advert newRealty) {
		return isFlatCommonFieldsUpdated(oldRealty, newRealty);
	}
	
	public static boolean isUpdatedFlatRentRealty(Advert oldRealty, Advert newRealty) {
		return isFlatCommonFieldsUpdated(oldRealty, newRealty);
	}
	
	
	public static boolean isFlatCommonFieldsUpdated(Advert oldRealty, Advert newRealty) {
		String oldText = String.valueOf(oldRealty.data.get("text"));
		String newText = String.valueOf(newRealty.data.get("text"));
		if (!ValuesUtils.isStringsEqual(oldText, newText)) {
			logger.info("Advert [{}] text changed from [{}] to [{}]", oldRealty.source.externalId, oldText, newText);
			return true;
		}
		
		//Есть проблема, что люди указывают ЖК и район, которые находятся в совершенно разных местах,
		//то есть район и район где находится ЖК не совпадают. Поэтому необходимо проверять данные параметры
		//возможно будут исправлены ошибки. Так как изначально тяжело понять где они ошиблись
		Mapper mapper = new Mapper();
		mapper.getConverters().addConverter(CalendarConverter.class);
		ResidentialComplex oldComplex = (DBObject)oldRealty.data.get("residentialComplex") != null ? mapper.fromDBObject(DB.DS(), ResidentialComplex.class, (DBObject)oldRealty.data.get("residentialComplex"), mapper.createEntityCache()) : null;
		ResidentialComplex newComplex = (DBObject)newRealty.data.get("residentialComplex") != null ? mapper.fromDBObject(DB.DS(), ResidentialComplex.class, (DBObject)newRealty.data.get("residentialComplex"), mapper.createEntityCache()) : null;
		//ResidentialComplex oldComplex = (ResidentialComplex)oldRealty.data.get("residentalComplex");
		//ResidentialComplex newComplex = (ResidentialComplex)newRealty.data.get("residentalComplex");
		
		if (oldComplex != null && newComplex != null) {
			if (oldComplex.id != null && newComplex.id != null) {
				if (!newComplex.id.equals(oldComplex.id)) {
					logger.info("Advert [{}] complex changed from [{}] to [{}]", oldRealty.source.externalId, oldComplex.id, newComplex.id);
					return true;
				}
			}
		}
		
		if (oldRealty.location != null && newRealty.location != null) {
			if (oldRealty.location.region != null && newRealty.location.region != null) {
				if (!oldRealty.location.region.code.equals(newRealty.location.region.code))
					return true;
			}
		}
		
		
		//Проверяем этаж
		Long oldFlatFloor = convertObjectoToLong(oldRealty.data.get("flatFloor"));
		Long newFlatFloor = convertObjectoToLong(newRealty.data.get("flatFloor"));
		if (!ValuesUtils.isLongsEqual(oldFlatFloor, newFlatFloor)) {
			logger.info("Advert [{}] flat floor changed from [{}] to [{}]", oldRealty.source.externalId, oldFlatFloor, newFlatFloor);
			return true;
		}
		
		//Проверяем площадь
		Float oldSquare = convertObjectoToFloat(oldRealty.data.get("square"));
		Float newSquare = convertObjectoToFloat(newRealty.data.get("square"));
		if (!ValuesUtils.isFloatsEqual(oldSquare, newSquare)) {
			logger.info("Advert [{}] square changed from [{}] to [{}]", oldRealty.source.externalId, oldSquare, newSquare);
			return true;
		}
		
		//Проверяем год постройки
		Long oldHourseYear = convertObjectoToLong(oldRealty.data.get("houseYear"));
		Long newHourseYear = convertObjectoToLong(newRealty.data.get("houseYear"));
		if (!ValuesUtils.isLongsEqual(oldHourseYear, newHourseYear)) {
			logger.info("Advert [{}] house year changed from [{}] to [{}]", oldRealty.source.externalId, oldHourseYear, newHourseYear);
			return true;
		}
		
		//Проверяем всего этажей
		Long oldHourseFloorCount = convertObjectoToLong(oldRealty.data.get("houseFloorCount"));
		Long newHourseFloorCount = convertObjectoToLong(newRealty.data.get("houseFloorCount"));
		if (!ValuesUtils.isLongsEqual(oldHourseFloorCount, newHourseFloorCount)) {
			logger.info("Advert [{}] house floor count changed from [{}] to [{}]", oldRealty.source.externalId, oldHourseFloorCount, newHourseFloorCount);
			return true;
		}
		
		//Проверяем количество комнат
		Float oldRooms = convertObjectoToFloat(oldRealty.data.get("rooms"));
		Float newRooms = convertObjectoToFloat(newRealty.data.get("rooms"));
		if (!ValuesUtils.isFloatsEqual(oldRooms, newRooms)) {
			logger.info("Advert [{}] rooms changed from [{}] to [{}]", oldRealty.source.externalId, oldRealty, newRooms);
			return true;
		}
		
		//Проверяем осталась ли прежняя цена
		Long oldPrice = convertObjectoToLong(oldRealty.data.get("price"));
		Long newPrice = convertObjectoToLong(newRealty.data.get("price"));
		
		if (!ValuesUtils.isLongsEqual(oldPrice, newPrice)) {
			logger.info("Advert [{}] price changed from [{}] to [{}]", oldRealty.source.externalId, oldPrice, newPrice);
			return true;
		}
		
		//Логика следующая. Как я понял то на кн поднимать и изменять можно раз в сутки.
		//Поэтому если уже вытаскивал данное объявление, то больше не надо. Оно уже проверенно.
		if (oldRealty.publishedAt.getTimeInMillis() - newRealty.publishedAt.getTimeInMillis() == 0l) {
			logger.info("Advert [{}] published date don't changed from [{}] to [{}]", oldRealty.source.externalId, oldRealty.publishedAt.getTime().toLocaleString(), newRealty.publishedAt.getTime().toLocaleString());
			return false;
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
	
}
