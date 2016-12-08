package kz.aphion.adverts.crawler.olx;

import kz.aphion.adverts.persistence.realty.Realty;
import kz.aphion.adverts.persistence.realty.data.flat.FlatRentRealty;
import kz.aphion.adverts.persistence.realty.data.flat.FlatSellRealty;
import kz.aphion.adverts.persistence.realty.types.RealtyOperationType;
import kz.aphion.adverts.persistence.realty.types.RealtyType;

import org.apache.commons.lang.NotImplementedException;

import play.Logger;

/**
 * Класс позволяет справнивать 2 объекта недвижимости на наличие различий или идентичности
 * 
 * @author artem.demidovich
 *
 * Created at May 20, 2016
 */
public class OlxRealtyComparator {

	/**
	 * Сравнивает две версии объектов и в случае если новая версия является более актульной возвращает true в противном случае false.
	 * Дополнительно методв выводит разницу если таковая имеется
	 * @param old
	 * @return
	 */
	public static Boolean isRealtyUpdated(Realty oldRealty, Realty newRealty) {
		if (oldRealty.type == RealtyType.FLAT && oldRealty.operation == RealtyOperationType.SELL)
			return isFlatSellRealtyUpdated((FlatSellRealty)oldRealty, (FlatSellRealty)newRealty);

		if (oldRealty.type == RealtyType.FLAT && oldRealty.operation == RealtyOperationType.RENT)
			return isFlatSellRealtyUpdated((FlatRentRealty)oldRealty, (FlatRentRealty)newRealty);


		throw new NotImplementedException("Comparator for [" + oldRealty.type + "] type not implemented");
	}
	
	public static Boolean isFlatSellRealtyUpdated(FlatSellRealty oldRealty, FlatSellRealty newRealty) {
		Boolean isUpdated = false;
		
		if (oldRealty.publishedAt.compareTo(newRealty.publishedAt) < 0) {
			isUpdated = true;
			Logger.info("Advert [%s] changed publishedAt from [%s] to [%s]", oldRealty.source.externalAdvertId, oldRealty.publishedAt.getTime().toString(), newRealty.publishedAt.getTime().toString());
		} else {
			if (oldRealty.publishedAt.compareTo(newRealty.publishedAt) == 0) {
				// Date is equal so we need to check data changes
				
				if (!isEqualsLongs(oldRealty.price, newRealty.price)) {
					isUpdated = true;
					Logger.info("Advert [%s] price changed from [%d] to [%d]", oldRealty.source.externalAdvertId, oldRealty.price, newRealty.price);
				}
				
				if (!oldRealty.location.region.code.equals(newRealty.location.region.code)) {
					isUpdated = true;
					Logger.info("Advert [%s] region changed from [%s] to [%s]", oldRealty.source.externalAdvertId, oldRealty.location.region.code.toString(), newRealty.location.region.code.toString());
				}
				
				if (!isEqualsLongs(oldRealty.data.flatFloor, newRealty.data.flatFloor)) {
					isUpdated = true;
					Logger.info("Advert [%s] flatFloor changed from [%d] to [%d]", oldRealty.source.externalAdvertId, oldRealty.data.flatFloor, newRealty.data.flatFloor);
				}
				
				if (!isEqualsFloats(oldRealty.data.rooms,newRealty.data.rooms)) {
					isUpdated = true;
					Logger.info("Advert [%s] rooms changed from [%f] to [%f]", oldRealty.source.externalAdvertId, oldRealty.data.rooms, newRealty.data.rooms);
				}
				
				if (!isEqualsLongs(oldRealty.data.houseFloorCount,newRealty.data.houseFloorCount)) {
					isUpdated = true;
					Logger.info("Advert [%s] houseFloorCount changed from [%d] to [%d]", oldRealty.source.externalAdvertId, oldRealty.data.houseFloorCount, newRealty.data.houseFloorCount);
				}
				
				if (!isEqualsFloats(oldRealty.data.square, newRealty.data.square)) {
					isUpdated = true;
					Logger.info("Advert [%s] square changed from [%f] to [%f]", oldRealty.source.externalAdvertId, oldRealty.data.square, newRealty.data.square);
				}
				
				if (!isEqualsFloats(oldRealty.data.squareLiving, newRealty.data.squareLiving)) {
					isUpdated = true;
					Logger.info("Advert [%s] squareLiving changed from [%f] to [%f]", oldRealty.source.externalAdvertId, oldRealty.data.squareLiving, newRealty.data.squareLiving);
				}
				
				if (!isEqualsFloats(oldRealty.data.squareKitchen, newRealty.data.squareKitchen)) {
					isUpdated = true;
					Logger.info("Advert [%s] squareKitchen changed from [%f] to [%f]", oldRealty.source.externalAdvertId, oldRealty.data.squareKitchen, newRealty.data.squareKitchen);
				}
				
				if (!isEqualsTexts(oldRealty.data.text, newRealty.data.text)) {
					isUpdated = true;
					Logger.info("Advert [%s] squareKitchen changed from [%s] to [%s]", oldRealty.source.externalAdvertId, oldRealty.data.text, newRealty.data.text);
				}
				
				// If publishedAt date the same
				if (!isEqualsBoolean(oldRealty.hasPhoto,newRealty.hasPhoto)) {
					isUpdated = true;
					Logger.info("Advert [%s] hasPhoto changed from [" + oldRealty.hasPhoto + "] to [" + newRealty.hasPhoto + "]", oldRealty.source.externalAdvertId);
				}
				
				// If publishedAt date the same
				if (oldRealty.photos != null && newRealty.photos != null) {
					if (oldRealty.photos.size() != newRealty.photos.size()) {
						isUpdated = true;
						Logger.info("Advert [%s] photos size changed from [" + oldRealty.photos.size() + "] to [" + newRealty.photos.size() + "]", oldRealty.source.externalAdvertId);
					}
				}
				
				if (oldRealty.publisher.phones.size() != newRealty.publisher.phones.size()) {
					isUpdated = true;
					Logger.info("Advert [%s] contact phones size changed from [" + oldRealty.publisher.phones.size() + "] to [" + newRealty.publisher.phones.size() + "]", oldRealty.source.externalAdvertId);
				}
				
			} else {
				// new Advert is older so there is no any changes
			}
		}
		
		return isUpdated;
	}
	
	public static Boolean isFlatSellRealtyUpdated(FlatRentRealty oldRealty, FlatRentRealty newRealty) {
		Boolean isUpdated = false;
		
		if (oldRealty.publishedAt.compareTo(newRealty.publishedAt) < 0) {
			isUpdated = true;
			Logger.info("Advert [%s] changed publishedAt from [%s] to [%s]", oldRealty.source.externalAdvertId, oldRealty.publishedAt.getTime().toString(), newRealty.publishedAt.getTime().toString());
		} else {
			if (oldRealty.publishedAt.compareTo(newRealty.publishedAt) == 0) {
				// Date is equal so we need to check data changes
				if (oldRealty.data.rentPeriod != newRealty.data.rentPeriod) {
					isUpdated = true;
					Logger.info("Advert [%s] rentOption changed from [%d] to [%d]", oldRealty.source.externalAdvertId, oldRealty.data.rentPeriod.toString(), newRealty.data.rentPeriod.toString());
				}
				
				if (!isEqualsLongs(oldRealty.price, newRealty.price)) {
					isUpdated = true;
					Logger.info("Advert [%s] price changed from [%d] to [%d]", oldRealty.source.externalAdvertId, oldRealty.price, newRealty.price);
				}
				
				if (!oldRealty.location.region.code.equals(newRealty.location.region.code)) {
					isUpdated = true;
					Logger.info("Advert [%s] region changed from [%s] to [%s]", oldRealty.source.externalAdvertId, oldRealty.location.region.code.toString(), newRealty.location.region.code.toString());
				}
				
				if (!isEqualsLongs(oldRealty.data.flatFloor, newRealty.data.flatFloor)) {
					isUpdated = true;
					Logger.info("Advert [%s] flatFloor changed from [%d] to [%d]", oldRealty.source.externalAdvertId, oldRealty.data.flatFloor, newRealty.data.flatFloor);
				}
				
				if (!isEqualsFloats(oldRealty.data.rooms,newRealty.data.rooms)) {
					isUpdated = true;
					Logger.info("Advert [%s] rooms changed from [%f] to [%f]", oldRealty.source.externalAdvertId, oldRealty.data.rooms, newRealty.data.rooms);
				}
				
				if (!isEqualsLongs(oldRealty.data.houseFloorCount,newRealty.data.houseFloorCount)) {
					isUpdated = true;
					Logger.info("Advert [%s] houseFloorCount changed from [%d] to [%d]", oldRealty.source.externalAdvertId, oldRealty.data.houseFloorCount, newRealty.data.houseFloorCount);
				}
				
				if (!isEqualsFloats(oldRealty.data.square, newRealty.data.square)) {
					isUpdated = true;
					Logger.info("Advert [%s] square changed from [%f] to [%f]", oldRealty.source.externalAdvertId, oldRealty.data.square, newRealty.data.square);
				}
				
				if (!isEqualsFloats(oldRealty.data.squareLiving, newRealty.data.squareLiving)) {
					isUpdated = true;
					Logger.info("Advert [%s] squareLiving changed from [%f] to [%f]", oldRealty.source.externalAdvertId, oldRealty.data.squareLiving, newRealty.data.squareLiving);
				}
				
				if (!isEqualsFloats(oldRealty.data.squareKitchen, newRealty.data.squareKitchen)) {
					isUpdated = true;
					Logger.info("Advert [%s] squareKitchen changed from [%f] to [%f]", oldRealty.source.externalAdvertId, oldRealty.data.squareKitchen, newRealty.data.squareKitchen);
				}
				
				if (!isEqualsTexts(oldRealty.data.text, newRealty.data.text)) {
					isUpdated = true;
					Logger.info("Advert [%s] squareKitchen changed from [%s] to [%s]", oldRealty.source.externalAdvertId, oldRealty.data.text, newRealty.data.text);
				}
				
				// If publishedAt date the same
				if (!isEqualsBoolean(oldRealty.hasPhoto,newRealty.hasPhoto)) {
					isUpdated = true;
					Logger.info("Advert [%s] hasPhoto changed from [" + oldRealty.hasPhoto + "] to [" + newRealty.hasPhoto + "]", oldRealty.source.externalAdvertId);
				}
				
				// If publishedAt date the same
				if (oldRealty.photos != null && newRealty.photos != null) {
					if (oldRealty.photos.size() != newRealty.photos.size()) {
						isUpdated = true;
						Logger.info("Advert [%s] photos size changed from [" + oldRealty.photos.size() + "] to [" + newRealty.photos.size() + "]", oldRealty.source.externalAdvertId);
					}
				}
				
			} else {
				// new Advert is older so there is no any changes
			}
		}
		
		return isUpdated;
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
