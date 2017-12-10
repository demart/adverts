package kz.aphion.adverts.analyser.comparator;

import kz.aphion.adverts.analyser.comparator.impl.FlatRentAdvertComparator;
import kz.aphion.adverts.analyser.comparator.impl.FlatSellAdvertComparator;
import kz.aphion.adverts.persistence.adverts.Advert;
import kz.aphion.adverts.persistence.adverts.AdvertOperationType;
import kz.aphion.adverts.persistence.adverts.AdvertType;
import kz.aphion.adverts.persistence.realty.RealtyType;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Фабрика занимающаяся порождением подходящих объектов для сравнения
 * 
 * @author artem.demidovich
 *
 * Created at Feb 27, 2017
 */
public class AdvertComparatorFactory {

	private static Logger logger = LoggerFactory.getLogger(AdvertComparatorFactory.class);
	
	/**
	 * Возвращает подходящий эксемпляр сравнивателей объектов
	 * @param realy
	 * @return
	 */
	public static AdvertComparator getAdvertComparatorInstance(Advert newAdvert, Advert oldAdvert) {
		if (newAdvert == null) 
			return null;
		
		if (newAdvert.type == AdvertType.REALTY && newAdvert.subType.equals(RealtyType.FLAT) && newAdvert.operation ==  AdvertOperationType.SELL)
			return new FlatSellAdvertComparator();
		
		if (newAdvert.type == AdvertType.REALTY && newAdvert.subType.equals(RealtyType.FLAT) && newAdvert.operation ==  AdvertOperationType.RENT)
			return new FlatRentAdvertComparator();
		
		throw new NotImplementedException();
	}
	
}
