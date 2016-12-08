package kz.aphion.adverts.crawler.irr.mappers;

import kz.aphion.adverts.persistence.realty.Realty;
import kz.aphion.adverts.persistence.realty.RealtyPhoto;
import kz.aphion.adverts.persistence.realty.data.flat.FlatRentRealty;
import kz.aphion.adverts.persistence.realty.data.flat.FlatSellRealty;

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
	public static boolean isUpdatedFlatSellRealty(Realty oldRealty, Realty newRealty) {
		
		//Проверяем описание
		FlatSellRealty oldDataRealty = (FlatSellRealty) oldRealty;
		FlatSellRealty newDataRealty = (FlatSellRealty) newRealty;

		
		
		if (oldDataRealty.data.text != null) {
			if (newDataRealty.data.text != null) {
				if (!oldDataRealty.data.text.equals(newDataRealty.data.text)) {
					logger.info("Adverts update price [{}] and [{}]", oldDataRealty.data.text, newDataRealty.data.text);
					return true;
				}
			}
		}
		
		
		//Проверяем осталась ли прежняя цена
		//Бывает что люди не указывают цену. Поэтому надо проверять указана ли она
		
		if (oldRealty.price == null && newRealty.price != null) {
			logger.info("Advert update price [0] to [{}]", newRealty.price);
			return true;
		}
		
		
		if (oldRealty.price != null && newRealty.price != null) {
			if (!oldRealty.price.equals(newRealty.price)) {
				logger.info("Adverts update price [{}] and [{}]", oldRealty.price, newRealty.price);
				return true;
			}
		}
		
		//Проверяем дату публикации. Если изменилась дата, то соответственно перезапишем объявление,
		//так как это показатель того. что оно еще актуально.
		if (oldRealty.publishedAt.getTimeInMillis() - newRealty.publishedAt.getTimeInMillis() < 0) {
			logger.info("Advert [{}] published date changed. [{}] and [{}]", oldRealty.source.externalAdvertId, oldRealty.publishedAt.getTime().toLocaleString(), newRealty.publishedAt.getTime().toLocaleString());
			return true;
		}
		
		//Проверяем количество комнат
		if (oldDataRealty.data.rooms == null && newDataRealty.data.rooms != null) {
			logger.info("Advert update live rooms [0] to [{}]", newDataRealty.data.rooms);
			return true;
		}
		
		
		if (newDataRealty.data.rooms != null && oldDataRealty.data.rooms != null) {
			if (!newDataRealty.data.rooms.equals(oldDataRealty.data.rooms)) {
				logger.info("Adverts update live rooms [{}] to [{}]", oldDataRealty.data.rooms, newDataRealty.data.rooms);
				return true;
			}
		}
		
		//Проверяем площадь
		if (oldDataRealty.data.square == null && newDataRealty.data.square != null) {
			logger.info("Advert update square [0] to [{}]", newDataRealty.data.square);
			return true;
		}
		
		
		if (newDataRealty.data.square != null && oldDataRealty.data.square != null) {
			if (!newDataRealty.data.square.equals(oldDataRealty.data.square)) {
				logger.info("Adverts update square [{}] to [{}]", oldDataRealty.data.square, newDataRealty.data.square);
				return true;
			}
		}
		
		//Проверяем год постройки
		if (oldDataRealty.data.houseYear == null && newDataRealty.data.houseYear != null) {
			logger.info("Advert update houseYear [0] to [{}]", newDataRealty.data.houseYear);
			return true;
		}
		
		if (newDataRealty.data.houseYear != null && oldDataRealty.data.houseYear != null) {
			if (!newDataRealty.data.houseYear.equals(oldDataRealty.data.houseYear)) {
				logger.info("Adverts update house year [{}] to [{}]", oldDataRealty.data.houseYear, newDataRealty.data.houseYear);
				return true;
			}
		}
		
		//Проверяем  жилую площадь
		if (oldDataRealty.data.squareLiving == null && newDataRealty.data.squareLiving != null) {
			logger.info("Advert update living square [0] to [{}]", newDataRealty.data.squareLiving);
			return true;
		}
		
		if (newDataRealty.data.squareLiving != null && oldDataRealty.data.squareLiving != null) {
			if (!newDataRealty.data.squareLiving.equals(oldDataRealty.data.squareLiving)) {
				logger.info("Adverts update square living [{}] to [{}]", oldDataRealty.data.squareLiving, newDataRealty.data.squareLiving);
				return true;
			}
		}
		
		//Проверяем  площадь кухни
		if (oldDataRealty.data.squareKitchen == null && newDataRealty.data.squareKitchen != null) {
			logger.info("Advert update kitchen square [0] to [{}]", newDataRealty.data.squareKitchen);
			return true;
		}
		
		if (newDataRealty.data.squareKitchen != null && oldDataRealty.data.squareKitchen != null) {
			if (!newDataRealty.data.squareKitchen.equals(oldDataRealty.data.squareKitchen)) {
				logger.info("Adverts update square kitchen [{}] to [{}]", oldDataRealty.data.squareKitchen, newDataRealty.data.squareKitchen);
				return true;
			}
		}
		
		//Проверяем  высоту потолков
		if (oldDataRealty.data.ceilingHeight == null && newDataRealty.data.ceilingHeight != null) {
			logger.info("Advert update ceiling height [0] to [{}]", newDataRealty.data.ceilingHeight);
			return true;
		}
		
		if (newDataRealty.data.ceilingHeight != null && oldDataRealty.data.ceilingHeight != null) {
			if (!newDataRealty.data.ceilingHeight.equals(oldDataRealty.data.ceilingHeight)) {
				logger.info("Adverts update ceiling height [{}] to [{}]", oldDataRealty.data.ceilingHeight, newDataRealty.data.ceilingHeight);
				return true;
			}
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
	
	/**
	 * Проверяем на обновление объявление в категории аренда квартир
	 * @param oldRealty
	 * @param newRealty
	 * @return
	 */
	public static boolean isUpdatedFlatRentRealty(Realty oldRealty, Realty newRealty) {
		
		FlatRentRealty oldDataRealty = (FlatRentRealty) oldRealty;
		FlatRentRealty newDataRealty = (FlatRentRealty) newRealty;

		
		//Проверяем описание		
		if (oldDataRealty.data.text != null) {
			if (newDataRealty.data.text != null) {
				if (!oldDataRealty.data.text.equals(newDataRealty.data.text)) {
					logger.info("Adverts update text [{}] and [{}]", oldDataRealty.data.text, newDataRealty.data.text);
					return true;
				}
			}
		}
		
		//Проверяем срок аренды
		if (oldDataRealty.data.rentPeriod == null && newDataRealty.data.rentPeriod != null) {
			logger.info("Advert update rent period [0] to [{}]", newDataRealty.data.rentPeriod);
			return true;
		}
		
		
		//Проверяем осталась ли прежняя цена
		//Бывает что люди не указывают цену. Поэтому надо проверять указана ли она
		
		if (oldRealty.price == null && newRealty.price != null) {
			logger.info("Advert update price [0] to [{}]", newRealty.price);
			return true;
		}
		
		
		if (oldRealty.price != null && newRealty.price != null) {
			if (!oldRealty.price.equals(newRealty.price)) {
				logger.info("Adverts update price [{}] and [{}]", oldRealty.price, newRealty.price);
				return true;
			}
		}
		
		//Проверяем дату публикации. Если изменилась дата, то соответственно перезапишем объявление,
		//так как это показатель того. что оно еще актуально.
		if (oldRealty.publishedAt.getTimeInMillis() - newRealty.publishedAt.getTimeInMillis() < 0) {
			logger.info("Advert [{}] published date changed. [{}] and [{}]", oldRealty.source.externalAdvertId, oldRealty.publishedAt.getTime().toLocaleString(), newRealty.publishedAt.getTime().toLocaleString());
			return true;
		}
		
		//Проверяем количество комнат
		if (oldDataRealty.data.rooms == null && newDataRealty.data.rooms != null) {
			logger.info("Advert update live rooms [0] to [{}]", newDataRealty.data.rooms);
			return true;
		}
		
		
		if (newDataRealty.data.rooms != null && oldDataRealty.data.rooms != null) {
			if (!newDataRealty.data.rooms.equals(oldDataRealty.data.rooms)) {
				logger.info("Adverts update live rooms [{}] to [{}]", oldDataRealty.data.rooms, newDataRealty.data.rooms);
				return true;
			}
		}
		
		//Проверяем площадь
		if (oldDataRealty.data.square == null && newDataRealty.data.square != null) {
			logger.info("Advert update square [0] to [{}]", newDataRealty.data.square);
			return true;
		}
		
		
		if (newDataRealty.data.square != null && oldDataRealty.data.square != null) {
			if (!newDataRealty.data.square.equals(oldDataRealty.data.square)) {
				logger.info("Adverts update square [{}] to [{}]", oldDataRealty.data.square, newDataRealty.data.square);
				return true;
			}
		}
		
		//Проверяем год постройки
		if (oldDataRealty.data.houseYear == null && newDataRealty.data.houseYear != null) {
			logger.info("Advert update houseYear [0] to [{}]", newDataRealty.data.houseYear);
			return true;
		}
		
		if (newDataRealty.data.houseYear != null && oldDataRealty.data.houseYear != null) {
			if (!newDataRealty.data.houseYear.equals(oldDataRealty.data.houseYear)) {
				logger.info("Adverts update house year [{}] to [{}]", oldDataRealty.data.houseYear, newDataRealty.data.houseYear);
				return true;
			}
		}
		
		//Проверяем  жилую площадь
		if (oldDataRealty.data.squareLiving == null && newDataRealty.data.squareLiving != null) {
			logger.info("Advert update living square [0] to [{}]", newDataRealty.data.squareLiving);
			return true;
		}
		
		if (newDataRealty.data.squareLiving != null && oldDataRealty.data.squareLiving != null) {
			if (!newDataRealty.data.squareLiving.equals(oldDataRealty.data.squareLiving)) {
				logger.info("Adverts update square living [{}] to [{}]", oldDataRealty.data.squareLiving, newDataRealty.data.squareLiving);
				return true;
			}
		}
		
		//Проверяем  площадь кухни
		if (oldDataRealty.data.squareKitchen == null && newDataRealty.data.squareKitchen != null) {
			logger.info("Advert update kitchen square [0] to [{}]", newDataRealty.data.squareKitchen);
			return true;
		}
		
		if (newDataRealty.data.squareKitchen != null && oldDataRealty.data.squareKitchen != null) {
			if (!newDataRealty.data.squareKitchen.equals(oldDataRealty.data.squareKitchen)) {
				logger.info("Adverts update square kitchen [{}] to [{}]", oldDataRealty.data.squareKitchen, newDataRealty.data.squareKitchen);
				return true;
			}
		}
		
		//Проверяем  высоту потолков
		if (oldDataRealty.data.ceilingHeight == null && newDataRealty.data.ceilingHeight != null) {
			logger.info("Advert update ceiling height [0] to [{}]", newDataRealty.data.ceilingHeight);
			return true;
		}
		
		if (newDataRealty.data.ceilingHeight != null && oldDataRealty.data.ceilingHeight != null) {
			if (!newDataRealty.data.ceilingHeight.equals(oldDataRealty.data.ceilingHeight)) {
				logger.info("Adverts update ceiling height [{}] to [{}]", oldDataRealty.data.ceilingHeight, newDataRealty.data.ceilingHeight);
				return true;
			}
		}
		
				
		// Фотографии редактируются отдельно и проходят проверку. 
		// Нужно сравнивать были ли изменения даже в случае если кол-во одинаковое 
		if ((oldRealty.photos != null && newRealty.photos == null) ||
				(oldRealty.photos == null && newRealty.photos != null)) {
			//Logger.error("photos");
			logger.info("Advert [{}] update array photos", oldRealty.source.externalAdvertId);
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
