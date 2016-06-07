package kz.aphion.adverts.crawler.kn.mappers;

import play.Logger;
import kz.aphion.adverts.crawler.kn.KnAdvertCategoryType;
import kz.aphion.adverts.persistence.realty.Realty;
import kz.aphion.adverts.persistence.realty.RealtyPhoto;
import kz.aphion.adverts.persistence.realty.data.flat.FlatRentRealty;
import kz.aphion.adverts.persistence.realty.data.flat.FlatSellRealty;

/**
 * Класс сравнивает 2 версии одинаковых объявлений для того, чтобы найти отличия
 *  
 * @author artem.demidovich
 *
 */
public class RealtyComparator {

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
					Logger.info("Adverts [%s] and [%s] have different description", oldRealty.source.externalAdvertId, newRealty.source.externalAdvertId);
					return true;
				}
			}
		}
		
		//Есть проблема, что люди указывают ЖК и район, которые находятся в совершенно разных местах,
		//то есть район и район где находится ЖК не совпадают. Поэтому необходимо проверять данные параметры
		//возможно будут исправлены ошибки. Так как изначально тяжело понять где они ошиблись
		if (oldDataRealty.data.residentalComplex != null) {
			if (newDataRealty.data.residentalComplex != null) {
				if (oldDataRealty.data.residentalComplex.relationId != null) {
					if (newDataRealty.data.residentalComplex.relationId != null) {
						if (!newDataRealty.data.residentalComplex.relationId.equals(oldDataRealty.data.residentalComplex.relationId)) {
							Logger.info("Advert [%s] complex changed from [%s] to [%s]", oldRealty.source.externalAdvertId, oldDataRealty.data.residentalComplex.relationId, newDataRealty.data.residentalComplex.relationId);
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
				Logger.info("Adverts [%s] floor changed from [%s] to [%s]", oldRealty.source.externalAdvertId, oldDataRealty.data.flatFloor, newDataRealty.data.flatFloor);
				return true;
			}
		}
		
		//Проверяем площадь
		if (oldDataRealty.data.square != null && newDataRealty.data.square  != null) {
			if (!oldDataRealty.data.square.equals(newDataRealty.data.square)) {
				Logger.info("Adverts [%s] square changed from [%s] to [%s]", oldRealty.source.externalAdvertId, oldDataRealty.data.square, newDataRealty.data.square);
				return true;
			}
		}
		
		//Проверяем год постройки
		if (oldDataRealty.data.houseYear != null && newDataRealty.data.houseYear  != null) {
			if (!oldDataRealty.data.houseYear.equals(newDataRealty.data.houseYear)) {
				Logger.info("Adverts [%s] house year changed from [%s] to [%s]", oldRealty.source.externalAdvertId, oldDataRealty.data.houseYear, newDataRealty.data.houseYear);
				return true;
			}
		}
		
		//Проверяем всего этажей
		if (oldDataRealty.data.houseFloorCount != null && newDataRealty.data.houseFloorCount  != null) {
			if (!oldDataRealty.data.houseFloorCount.equals(newDataRealty.data.houseFloorCount)) {
				Logger.info("Adverts [%s] house floor count changed from [%s] to [%s]", oldRealty.source.externalAdvertId, oldDataRealty.data.houseFloorCount, newDataRealty.data.houseFloorCount);
				return true;
			}
		}
		
		//Проверяем количество комнат
		if (oldDataRealty.data.rooms != null && newDataRealty.data.rooms  != null) {
			if (!oldDataRealty.data.rooms.equals(newDataRealty.data.rooms)) {
				Logger.info("Adverts [%s] house floor count changed from [%s] to [%s]", oldRealty.source.externalAdvertId, oldDataRealty.data.rooms, newDataRealty.data.rooms);
				return true;
			}
		}
		
		//Проверяем осталась ли прежняя цена
		if (!oldRealty.price.equals(newRealty.price)) {
			Logger.info("Adverts [%s] price changed from [%s] to [%s]", oldRealty.source.externalAdvertId, oldRealty.price, newRealty.price);
			return true;
		}
		
		//Логика следующая. Как я понял то на кн поднимать и изменять можно раз в сутки.
		//Поэтому если уже вытаскивал данное объявление, то больше не надо. Оно уже проверенно.
		if (oldRealty.publishedAt.getTimeInMillis() - newRealty.publishedAt.getTimeInMillis() == 0l) {
			Logger.info("Advert [%s] published date don't changed. [%s] and [%s]", oldRealty.source.externalAdvertId, oldRealty.publishedAt.getTime().toLocaleString(), newRealty.publishedAt.getTime().toLocaleString());
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
				Logger.info("Advert [%s] photos size changed from [%d] to [%d]", oldRealty.source.externalAdvertId, oldRealty.photos.size(), newRealty.photos.size());
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
					Logger.info("Advert [%s] photos path changed", oldRealty.source.externalAdvertId, oldRealty.photos.size(), newRealty.photos.size());
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
					Logger.info("Adverts [%s] and [%s] have different description", oldRealty.source.externalAdvertId, newRealty.source.externalAdvertId);
					return true;
				}
			}
		}
		
		//Есть проблема, что люди указывают ЖК и район, которые находятся в совершенно разных местах,
		//то есть район и район где находится ЖК не совпадают. Поэтому необходимо проверять данные параметры
		//возможно будут исправлены ошибки. Так как изначально тяжело понять где они ошиблись
		if (oldDataRealty.data.residentalComplex != null) {
			if (newDataRealty.data.residentalComplex != null) {
				if (oldDataRealty.data.residentalComplex.relationId != null) {
					if (newDataRealty.data.residentalComplex.relationId != null) {
						if (!newDataRealty.data.residentalComplex.relationId.equals(oldDataRealty.data.residentalComplex.relationId)) {
							Logger.info("Advert [%s] complex changed from [%s] to [%s]", oldRealty.source.externalAdvertId, oldDataRealty.data.residentalComplex.relationId, newDataRealty.data.residentalComplex.relationId);
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
				Logger.info("Adverts [%s] floor changed from [%s] to [%s]", oldRealty.source.externalAdvertId, oldDataRealty.data.flatFloor, newDataRealty.data.flatFloor);
				return true;
			}
		}
		
		//Проверяем площадь
		if (oldDataRealty.data.square != null && newDataRealty.data.square  != null) {
			if (!oldDataRealty.data.square.equals(newDataRealty.data.square)) {
				Logger.info("Adverts [%s] square changed from [%s] to [%s]", oldRealty.source.externalAdvertId, oldDataRealty.data.square, newDataRealty.data.square);
				return true;
			}
		}
		
		//Проверяем год постройки
		if (oldDataRealty.data.houseYear != null && newDataRealty.data.houseYear  != null) {
			if (!oldDataRealty.data.houseYear.equals(newDataRealty.data.houseYear)) {
				Logger.info("Adverts [%s] house year changed from [%s] to [%s]", oldRealty.source.externalAdvertId, oldDataRealty.data.houseYear, newDataRealty.data.houseYear);
				return true;
			}
		}
		
		//Проверяем всего этажей
		if (oldDataRealty.data.houseFloorCount != null && newDataRealty.data.houseFloorCount  != null) {
			if (!oldDataRealty.data.houseFloorCount.equals(newDataRealty.data.houseFloorCount)) {
				Logger.info("Adverts [%s] house floor count changed from [%s] to [%s]", oldRealty.source.externalAdvertId, oldDataRealty.data.houseFloorCount, newDataRealty.data.houseFloorCount);
				return true;
			}
		}
		
		//Проверяем осталась ли прежняя цена
		if (!oldRealty.price.equals(newRealty.price)) {
			Logger.info("Adverts [%s] price changed from [%s] to [%s]", oldRealty.source.externalAdvertId, oldRealty.price, newRealty.price);
			return true;
		}
		
		//Логика следующая. Как я понял то на кн поднимать и изменять можно раз в сутки.
		//Поэтому если уже вытаскивал данное объявление, то больше не надо. Оно уже проверенно.
		if (oldRealty.publishedAt.getTimeInMillis() - newRealty.publishedAt.getTimeInMillis() == 0l) {
			Logger.info("Advert [%s] published date don't changed. [%s] and [%s]", oldRealty.source.externalAdvertId, oldRealty.publishedAt.getTime().toLocaleString(), newRealty.publishedAt.getTime().toLocaleString());
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
				Logger.info("Advert [%s] photos size changed from [%d] to [%d]", oldRealty.source.externalAdvertId, oldRealty.photos.size(), newRealty.photos.size());
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
					Logger.info("Advert [%s] photos path changed", oldRealty.source.externalAdvertId, oldRealty.photos.size(), newRealty.photos.size());
					return true;
				}
			}
			
		}
		
		return false;
	}
	
}
