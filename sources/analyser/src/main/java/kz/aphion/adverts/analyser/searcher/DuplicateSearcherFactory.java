package kz.aphion.adverts.analyser.searcher;

import kz.aphion.adverts.analyser.searcher.impl.FlatRentAdvertDuplicateSearcher;
import kz.aphion.adverts.analyser.searcher.impl.FlatSellAdvertDuplicateSearcher;
import kz.aphion.adverts.persistence.adverts.Advert;
import kz.aphion.adverts.persistence.adverts.AdvertOperationType;
import kz.aphion.adverts.persistence.adverts.AdvertType;
import kz.aphion.adverts.persistence.realty.RealtyType;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Фабрика возвращает конкретный алгоритм поиска дубликатов по типу объявления
 * @author artem.demidovich
 *
 * Created at Jun 12, 2016
 */
public class DuplicateSearcherFactory {

	private static Logger logger = LoggerFactory.getLogger(DuplicateSearcherFactory.class);
	
	/**
	 * Возвращает подходящий эксемпляр искателя дубликатов
	 * @param realy
	 * @return
	 */
	public static DuplicateSearcher getDuplicateSearcherInstance(Advert newAdvert, Advert oldAdvert) {
		if (newAdvert == null) 
			return null;
		
		if (newAdvert.type == AdvertType.REALTY && newAdvert.subType.equals(RealtyType.FLAT) && newAdvert.operation ==  AdvertOperationType.SELL)
			return new FlatSellAdvertDuplicateSearcher();
		
		if (newAdvert.type == AdvertType.REALTY && newAdvert.subType.equals(RealtyType.FLAT) && newAdvert.operation ==  AdvertOperationType.RENT)
			return new FlatRentAdvertDuplicateSearcher();
		
		throw new NotImplementedException();
	}
	
}
