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
		
		//Проверяем осталась ли прежняя цена
		if (!oldRealty.price.equals(newRealty.price)) {
			Logger.info("Adverts [%s] and [%s] have different price", oldRealty.source.externalAdvertId, newRealty.source.externalAdvertId);
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
		
		//Проверяем осталась ли прежняя цена
		if (!oldRealty.price.equals(newRealty.price)) {
			Logger.info("Adverts [%s] and [%s] have different price", oldRealty.source.externalAdvertId, newRealty.source.externalAdvertId);
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
