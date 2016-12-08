package kz.aphion.adverts.crawler.krisha.mappers;


import kz.aphion.adverts.persistence.realty.Realty;
import kz.aphion.adverts.persistence.realty.RealtyPhoto;

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
	 * 
	 * @param existing Существующее объявление
	 * @param updated Новое объявление полученное из источника
	 * @return
	 */
	public static boolean isUpdated(Realty oldRealty, Realty newRealty) {
		// Если версия изменилась в большую сторону
		// Судя по их логике меняется даже в случае если я ничего не меняю
		// 
		Long oldVersion = Long.valueOf(oldRealty.source.sourceDataVersion);
		Long newVersion = Long.valueOf(newRealty.source.sourceDataVersion);
		if (oldVersion < newVersion) {
			logger.info("Advert [{}] data verison changed from [{}] to [{}]", oldRealty.source.externalAdvertId, oldVersion, newVersion);
			return true;
		}
		
		if (oldRealty.publishedAt.compareTo(newRealty.publishedAt) < 0) {
			// Даты не совпадают
			logger.info("Advert [{}] published date changed from [{}] to [{}]", oldRealty.source.externalAdvertId, oldRealty.publishedAt.getTime().toLocaleString(), newRealty.publishedAt.getTime().toLocaleString());
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
