package kz.aphion.adverts.crawler.kn.mappers;


import kz.aphion.adverts.persistence.realty.Realty;
import kz.aphion.adverts.persistence.realty.RealtyPhoto;
import kz.aphion.adverts.persistence.realty.data.flat.FlatRentRealty;
import kz.aphion.adverts.persistence.realty.data.flat.FlatSellRealty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	public static boolean isUpdatedFlatSellRealty(Realty oldRealty, Realty newRealty) {
		
		//Проверяем описание
		FlatSellRealty oldDataRealty = (FlatSellRealty) oldRealty;
		FlatSellRealty newDataRealty = (FlatSellRealty) newRealty;

		
		
		if (oldDataRealty.data.text != null) {
			if (newDataRealty.data.text != null) {
				if (!oldDataRealty.data.text.equals(newDataRealty.data.text)) {
					logger.info("Adverts [{}] and [{}] have different description", oldRealty.source.externalAdvertId, newRealty.source.externalAdvertId);
					return true;
				}
			}
		}
		
		//Есть проблема, что люди указывают ЖК и район, которые находятся в совершенно разных местах,
		//то есть район и район где находится ЖК не совпадают. Поэтому необходимо проверять данные параметры
		//возможно будут исправлены ошибки. Так как изначально тяжело понять где они ошиблись
		if (oldDataRealty.data.residentalComplex != null) {
			if (newDataRealty.data.residentalComplex != null) {
				if (oldDataRealty.data.residentalComplex.id != null) {
					if (newDataRealty.data.residentalComplex.id != null) {
						if (!newDataRealty.data.residentalComplex.id.equals(oldDataRealty.data.residentalComplex.id)) {
							logger.info("Advert [{}] complex changed from [{}] to [{}]", oldRealty.source.externalAdvertId, oldDataRealty.data.residentalComplex.id, newDataRealty.data.residentalComplex.id);
							return true;
						}
					}
				}
			}
		}
		
		if (oldDataRealty.location != null && newDataRealty.location != null) {
			if (oldDataRealty.location.region != null && newDataRealty.location.region != null) {
				if (!oldDataRealty.location.region.code.equals(newDataRealty.location.region.code))
					return true;
			}
		}
		
		//Проверяем этаж
		if (oldDataRealty.data.flatFloor != null && newDataRealty.data.flatFloor != null) {
			if (!oldDataRealty.data.flatFloor.equals(newDataRealty.data.flatFloor)) {
				logger.info("Adverts [{}] floor changed from [{}] to [{}]", oldRealty.source.externalAdvertId, oldDataRealty.data.flatFloor, newDataRealty.data.flatFloor);
				return true;
			}
		}
		
		//Проверяем площадь
		if (oldDataRealty.data.square != null && newDataRealty.data.square  != null) {
			if (!oldDataRealty.data.square.equals(newDataRealty.data.square)) {
				logger.info("Adverts [{}] square changed from [{}] to [{}]", oldRealty.source.externalAdvertId, oldDataRealty.data.square, newDataRealty.data.square);
				return true;
			}
		}
		
		//Проверяем год постройки
		if (oldDataRealty.data.houseYear != null && newDataRealty.data.houseYear  != null) {
			if (!oldDataRealty.data.houseYear.equals(newDataRealty.data.houseYear)) {
				logger.info("Adverts [{}] house year changed from [{}] to [{}]", oldRealty.source.externalAdvertId, oldDataRealty.data.houseYear, newDataRealty.data.houseYear);
				return true;
			}
		}
		
		//Проверяем всего этажей
		if (oldDataRealty.data.houseFloorCount != null && newDataRealty.data.houseFloorCount  != null) {
			if (!oldDataRealty.data.houseFloorCount.equals(newDataRealty.data.houseFloorCount)) {
				logger.info("Adverts [{}] house floor count changed from [{}] to [{}]", oldRealty.source.externalAdvertId, oldDataRealty.data.houseFloorCount, newDataRealty.data.houseFloorCount);
				return true;
			}
		}
		
		//Проверяем количество комнат
		if (oldDataRealty.data.rooms != null && newDataRealty.data.rooms  != null) {
			if (!oldDataRealty.data.rooms.equals(newDataRealty.data.rooms)) {
				logger.info("Adverts [{}] house floor count changed from [{}] to [{}]", oldRealty.source.externalAdvertId, oldDataRealty.data.rooms, newDataRealty.data.rooms);
				return true;
			}
		}
		
		//Проверяем осталась ли прежняя цена
		if (!oldRealty.price.equals(newRealty.price)) {
			logger.info("Adverts [{}] price changed from [{}] to [{}]", oldRealty.source.externalAdvertId, oldRealty.price, newRealty.price);
			return true;
		}
		
		//Логика следующая. Как я понял то на кн поднимать и изменять можно раз в сутки.
		//Поэтому если уже вытаскивал данное объявление, то больше не надо. Оно уже проверенно.
		if (oldRealty.publishedAt.getTimeInMillis() - newRealty.publishedAt.getTimeInMillis() == 0l) {
			logger.info("Advert [{}] published date don't changed. [{}] and [{}]", oldRealty.source.externalAdvertId, oldRealty.publishedAt.getTime().toLocaleString(), newRealty.publishedAt.getTime().toLocaleString());
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
				logger.info("Advert [{}] photos size changed from [{}] to [{}]", oldRealty.source.externalAdvertId, oldRealty.photos.size(), newRealty.photos.size());
				return true;
			}
			
			for (RealtyPhoto oldPhoto : oldRealty.photos) {
				boolean wasFound = false;
				for (RealtyPhoto newPhoto : newRealty.photos) {
					if (newPhoto.path.equalsIgnoreCase(oldPhoto.path))
						wasFound = true;
				}
				// Если нашли разницу
				if (wasFound == false) {
					logger.info("Advert [{}] photos path changed", oldRealty.source.externalAdvertId, oldRealty.photos.size(), newRealty.photos.size());
					return true;
				}
			}
			
		}
		
		return false;
	}
	
	public static boolean isUpdatedFlatRentRealty(Realty oldRealty, Realty newRealty) {
		
		//Проверяем описание
		FlatRentRealty oldDataRealty = (FlatRentRealty) oldRealty;
		FlatRentRealty newDataRealty = (FlatRentRealty) newRealty;
		
		if (oldDataRealty.data.text != null) {
			if (newDataRealty.data.text != null) {
				if (!oldDataRealty.data.text.equals(newDataRealty.data.text)) {
					logger.info("Adverts [{}] and [{}] have different description", oldRealty.source.externalAdvertId, newRealty.source.externalAdvertId);
					return true;
				}
			}
		}
		
		//Есть проблема, что люди указывают ЖК и район, которые находятся в совершенно разных местах,
		//то есть район и район где находится ЖК не совпадают. Поэтому необходимо проверять данные параметры
		//возможно будут исправлены ошибки. Так как изначально тяжело понять где они ошиблись
		if (oldDataRealty.data.residentalComplex != null) {
			if (newDataRealty.data.residentalComplex != null) {
				if (oldDataRealty.data.residentalComplex.id != null) {
					if (newDataRealty.data.residentalComplex.id != null) {
						if (!newDataRealty.data.residentalComplex.id.equals(oldDataRealty.data.residentalComplex.id)) {
							logger.info("Advert [{}] complex changed from [{}] to [{}]", oldRealty.source.externalAdvertId, oldDataRealty.data.residentalComplex.id, newDataRealty.data.residentalComplex.id);
							return true;
						}
					}
				}
			}
		}
		
		if (oldDataRealty.location != null && newDataRealty.location != null) {
			if (oldDataRealty.location.region != null && newDataRealty.location.region != null) {
				if (!oldDataRealty.location.region.code.equals(newDataRealty.location.region.code))
					return true;
			}
		}
		
		
		//Проверяем этаж
		if (oldDataRealty.data.flatFloor != null && newDataRealty.data.flatFloor != null) {
			if (!oldDataRealty.data.flatFloor.equals(newDataRealty.data.flatFloor)) {
				logger.info("Adverts [{}] floor changed from [{}] to [{}]", oldRealty.source.externalAdvertId, oldDataRealty.data.flatFloor, newDataRealty.data.flatFloor);
				return true;
			}
		}
		
		//Проверяем площадь
		if (oldDataRealty.data.square != null && newDataRealty.data.square  != null) {
			if (!oldDataRealty.data.square.equals(newDataRealty.data.square)) {
				logger.info("Adverts [{}] square changed from [{}] to [{}]", oldRealty.source.externalAdvertId, oldDataRealty.data.square, newDataRealty.data.square);
				return true;
			}
		}
		
		//Проверяем год постройки
		if (oldDataRealty.data.houseYear != null && newDataRealty.data.houseYear  != null) {
			if (!oldDataRealty.data.houseYear.equals(newDataRealty.data.houseYear)) {
				logger.info("Adverts [{}] house year changed from [{}] to [{}]", oldRealty.source.externalAdvertId, oldDataRealty.data.houseYear, newDataRealty.data.houseYear);
				return true;
			}
		}
		
		//Проверяем всего этажей
		if (oldDataRealty.data.houseFloorCount != null && newDataRealty.data.houseFloorCount  != null) {
			if (!oldDataRealty.data.houseFloorCount.equals(newDataRealty.data.houseFloorCount)) {
				logger.info("Adverts [{}] house floor count changed from [{}] to [{}]", oldRealty.source.externalAdvertId, oldDataRealty.data.houseFloorCount, newDataRealty.data.houseFloorCount);
				return true;
			}
		}
		
		//Проверяем осталась ли прежняя цена
		if (!oldRealty.price.equals(newRealty.price)) {
			logger.info("Adverts [{}] price changed from [{}] to [{}]", oldRealty.source.externalAdvertId, oldRealty.price, newRealty.price);
			return true;
		}
		
		//Логика следующая. Как я понял то на кн поднимать и изменять можно раз в сутки.
		//Поэтому если уже вытаскивал данное объявление, то больше не надо. Оно уже проверенно.
		if (oldRealty.publishedAt.getTimeInMillis() - newRealty.publishedAt.getTimeInMillis() == 0l) {
			logger.info("Advert [{}] published date don't changed. [{}] and [{}]", oldRealty.source.externalAdvertId, oldRealty.publishedAt.getTime().toLocaleString(), newRealty.publishedAt.getTime().toLocaleString());
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
				logger.info("Advert [{}] photos size changed from [{}] to [{}]", oldRealty.source.externalAdvertId, oldRealty.photos.size(), newRealty.photos.size());
				return true;
			}
			
			for (RealtyPhoto oldPhoto : oldRealty.photos) {
				boolean wasFound = false;
				for (RealtyPhoto newPhoto : newRealty.photos) {
					if (newPhoto.path.equalsIgnoreCase(oldPhoto.path))
						wasFound = true;
				}
				// Если нашли разницу
				if (wasFound == false) {
					logger.info("Advert [{}] photos path changed", oldRealty.source.externalAdvertId, oldRealty.photos.size(), newRealty.photos.size());
					return true;
				}
			}
			
		}
		
		return false;
	}
	
}
