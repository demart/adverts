package kz.aphion.adverts.crawler.irr.mappers;

import kz.aphion.adverts.persistence.realty.Realty;
import kz.aphion.adverts.persistence.realty.data.flat.FlatRentRealty;
import kz.aphion.adverts.persistence.realty.data.flat.FlatSellRealty;
import kz.aphion.adverts.persistence.realty.RealtyPhoto;
import play.Logger;

public class RealtyComparator {

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
					Logger.info("Adverts update price [%s] and [%s]", oldDataRealty.data.text, newDataRealty.data.text);
					return true;
				}
			}
		}
		
		
		//Проверяем осталась ли прежняя цена
		//Бывает что люди не указывают цену. Поэтому надо проверять указана ли она
		
		if (oldRealty.price == null && newRealty.price != null) {
			Logger.info("Advert update price [0] to [%s]", newRealty.price);
			return true;
		}
		
		
		if (oldRealty.price != null && newRealty.price != null) {
			if (!oldRealty.price.equals(newRealty.price)) {
				Logger.info("Adverts update price [%s] and [%s]", oldRealty.price, newRealty.price);
				return true;
			}
		}
		
		//Проверяем дату публикации. Если изменилась дата, то соответственно перезапишем объявление,
		//так как это показатель того. что оно еще актуально.
		if (oldRealty.publishedAt.getTimeInMillis() - newRealty.publishedAt.getTimeInMillis() < 0) {
			Logger.info("Advert [%s] published date changed. [%s] and [%s]", oldRealty.source.externalAdvertId, oldRealty.publishedAt.getTime().toLocaleString(), newRealty.publishedAt.getTime().toLocaleString());
			return true;
		}
		
		//Проверяем количество комнат
		if (oldDataRealty.data.rooms == null && newDataRealty.data.rooms != null) {
			Logger.info("Advert update live rooms [0] to [%s]", newDataRealty.data.rooms);
			return true;
		}
		
		
		if (newDataRealty.data.rooms != null && oldDataRealty.data.rooms != null) {
			if (!newDataRealty.data.rooms.equals(oldDataRealty.data.rooms)) {
				Logger.info("Adverts update live rooms [%s] to [%s]", oldDataRealty.data.rooms, newDataRealty.data.rooms);
				return true;
			}
		}
		
		//Проверяем площадь
		if (oldDataRealty.data.square == null && newDataRealty.data.square != null) {
			Logger.info("Advert update square [0] to [%s]", newDataRealty.data.square);
			return true;
		}
		
		
		if (newDataRealty.data.square != null && oldDataRealty.data.square != null) {
			if (!newDataRealty.data.square.equals(oldDataRealty.data.square)) {
				Logger.info("Adverts update square [%s] to [%s]", oldDataRealty.data.square, newDataRealty.data.square);
				return true;
			}
		}
		
		//Проверяем год постройки
		if (oldDataRealty.data.houseYear == null && newDataRealty.data.houseYear != null) {
			Logger.info("Advert update houseYear [0] to [%s]", newDataRealty.data.houseYear);
			return true;
		}
		
		if (newDataRealty.data.houseYear != null && oldDataRealty.data.houseYear != null) {
			if (!newDataRealty.data.houseYear.equals(oldDataRealty.data.houseYear)) {
				Logger.info("Adverts update house year [%s] to [%s]", oldDataRealty.data.houseYear, newDataRealty.data.houseYear);
				return true;
			}
		}
		
		//Проверяем  жилую площадь
		if (oldDataRealty.data.squareLiving == null && newDataRealty.data.squareLiving != null) {
			Logger.info("Advert update living square [0] to [%s]", newDataRealty.data.squareLiving);
			return true;
		}
		
		if (newDataRealty.data.squareLiving != null && oldDataRealty.data.squareLiving != null) {
			if (!newDataRealty.data.squareLiving.equals(oldDataRealty.data.squareLiving)) {
				Logger.info("Adverts update square living [%s] to [%s]", oldDataRealty.data.squareLiving, newDataRealty.data.squareLiving);
				return true;
			}
		}
		
		//Проверяем  площадь кухни
		if (oldDataRealty.data.squareKitchen == null && newDataRealty.data.squareKitchen != null) {
			Logger.info("Advert update kitchen square [0] to [%s]", newDataRealty.data.squareKitchen);
			return true;
		}
		
		if (newDataRealty.data.squareKitchen != null && oldDataRealty.data.squareKitchen != null) {
			if (!newDataRealty.data.squareKitchen.equals(oldDataRealty.data.squareKitchen)) {
				Logger.info("Adverts update square kitchen [%s] to [%s]", oldDataRealty.data.squareKitchen, newDataRealty.data.squareKitchen);
				return true;
			}
		}
		
		//Проверяем  высоту потолков
		if (oldDataRealty.data.ceilingHeight == null && newDataRealty.data.ceilingHeight != null) {
			Logger.info("Advert update ceiling height [0] to [%s]", newDataRealty.data.ceilingHeight);
			return true;
		}
		
		if (newDataRealty.data.ceilingHeight != null && oldDataRealty.data.ceilingHeight != null) {
			if (!newDataRealty.data.ceilingHeight.equals(oldDataRealty.data.ceilingHeight)) {
				Logger.info("Adverts update ceiling height [%s] to [%s]", oldDataRealty.data.ceilingHeight, newDataRealty.data.ceilingHeight);
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
					Logger.info("Adverts update text [%s] and [%s]", oldDataRealty.data.text, newDataRealty.data.text);
					return true;
				}
			}
		}
		
		//Проверяем срок аренды
		if (oldDataRealty.data.rentPeriod == null && newDataRealty.data.rentPeriod != null) {
			Logger.info("Advert update rent period [0] to [%s]", newDataRealty.data.rentPeriod);
			return true;
		}
		
		
		//Проверяем осталась ли прежняя цена
		//Бывает что люди не указывают цену. Поэтому надо проверять указана ли она
		
		if (oldRealty.price == null && newRealty.price != null) {
			Logger.info("Advert update price [0] to [%s]", newRealty.price);
			return true;
		}
		
		
		if (oldRealty.price != null && newRealty.price != null) {
			if (!oldRealty.price.equals(newRealty.price)) {
				Logger.info("Adverts update price [%s] and [%s]", oldRealty.price, newRealty.price);
				return true;
			}
		}
		
		//Проверяем дату публикации. Если изменилась дата, то соответственно перезапишем объявление,
		//так как это показатель того. что оно еще актуально.
		if (oldRealty.publishedAt.getTimeInMillis() - newRealty.publishedAt.getTimeInMillis() < 0) {
			Logger.info("Advert [%s] published date changed. [%s] and [%s]", oldRealty.source.externalAdvertId, oldRealty.publishedAt.getTime().toLocaleString(), newRealty.publishedAt.getTime().toLocaleString());
			return true;
		}
		
		//Проверяем количество комнат
		if (oldDataRealty.data.rooms == null && newDataRealty.data.rooms != null) {
			Logger.info("Advert update live rooms [0] to [%s]", newDataRealty.data.rooms);
			return true;
		}
		
		
		if (newDataRealty.data.rooms != null && oldDataRealty.data.rooms != null) {
			if (!newDataRealty.data.rooms.equals(oldDataRealty.data.rooms)) {
				Logger.info("Adverts update live rooms [%s] to [%s]", oldDataRealty.data.rooms, newDataRealty.data.rooms);
				return true;
			}
		}
		
		//Проверяем площадь
		if (oldDataRealty.data.square == null && newDataRealty.data.square != null) {
			Logger.info("Advert update square [0] to [%s]", newDataRealty.data.square);
			return true;
		}
		
		
		if (newDataRealty.data.square != null && oldDataRealty.data.square != null) {
			if (!newDataRealty.data.square.equals(oldDataRealty.data.square)) {
				Logger.info("Adverts update square [%s] to [%s]", oldDataRealty.data.square, newDataRealty.data.square);
				return true;
			}
		}
		
		//Проверяем год постройки
		if (oldDataRealty.data.houseYear == null && newDataRealty.data.houseYear != null) {
			Logger.info("Advert update houseYear [0] to [%s]", newDataRealty.data.houseYear);
			return true;
		}
		
		if (newDataRealty.data.houseYear != null && oldDataRealty.data.houseYear != null) {
			if (!newDataRealty.data.houseYear.equals(oldDataRealty.data.houseYear)) {
				Logger.info("Adverts update house year [%s] to [%s]", oldDataRealty.data.houseYear, newDataRealty.data.houseYear);
				return true;
			}
		}
		
		//Проверяем  жилую площадь
		if (oldDataRealty.data.squareLiving == null && newDataRealty.data.squareLiving != null) {
			Logger.info("Advert update living square [0] to [%s]", newDataRealty.data.squareLiving);
			return true;
		}
		
		if (newDataRealty.data.squareLiving != null && oldDataRealty.data.squareLiving != null) {
			if (!newDataRealty.data.squareLiving.equals(oldDataRealty.data.squareLiving)) {
				Logger.info("Adverts update square living [%s] to [%s]", oldDataRealty.data.squareLiving, newDataRealty.data.squareLiving);
				return true;
			}
		}
		
		//Проверяем  площадь кухни
		if (oldDataRealty.data.squareKitchen == null && newDataRealty.data.squareKitchen != null) {
			Logger.info("Advert update kitchen square [0] to [%s]", newDataRealty.data.squareKitchen);
			return true;
		}
		
		if (newDataRealty.data.squareKitchen != null && oldDataRealty.data.squareKitchen != null) {
			if (!newDataRealty.data.squareKitchen.equals(oldDataRealty.data.squareKitchen)) {
				Logger.info("Adverts update square kitchen [%s] to [%s]", oldDataRealty.data.squareKitchen, newDataRealty.data.squareKitchen);
				return true;
			}
		}
		
		//Проверяем  высоту потолков
		if (oldDataRealty.data.ceilingHeight == null && newDataRealty.data.ceilingHeight != null) {
			Logger.info("Advert update ceiling height [0] to [%s]", newDataRealty.data.ceilingHeight);
			return true;
		}
		
		if (newDataRealty.data.ceilingHeight != null && oldDataRealty.data.ceilingHeight != null) {
			if (!newDataRealty.data.ceilingHeight.equals(oldDataRealty.data.ceilingHeight)) {
				Logger.info("Adverts update ceiling height [%s] to [%s]", oldDataRealty.data.ceilingHeight, newDataRealty.data.ceilingHeight);
				return true;
			}
		}
		
				
		// Фотографии редактируются отдельно и проходят проверку. 
		// Нужно сравнивать были ли изменения даже в случае если кол-во одинаковое 
		if ((oldRealty.photos != null && newRealty.photos == null) ||
				(oldRealty.photos == null && newRealty.photos != null)) {
			//Logger.error("photos");
			Logger.info("Advert [%s] update array photos", oldRealty.source.externalAdvertId);
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
