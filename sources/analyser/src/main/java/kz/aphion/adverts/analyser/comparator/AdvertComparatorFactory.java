package kz.aphion.adverts.analyser.comparator;

import kz.aphion.adverts.analyser.comparator.impl.FlatRentAdvertComparator;
import kz.aphion.adverts.analyser.comparator.impl.FlatSellAdvertComparator;
import kz.aphion.adverts.analyser.mq.ProcessRealtyModel;
import kz.aphion.adverts.persistence.realty.types.RealtyOperationType;
import kz.aphion.adverts.persistence.realty.types.RealtyType;

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
	public static AdvertComparator getAdvertComparatorInstance(ProcessRealtyModel model) {
		if (model.type == RealtyType.FLAT && model.operation == RealtyOperationType.SELL)
			return new FlatSellAdvertComparator();
		if (model.type == RealtyType.FLAT && model.operation == RealtyOperationType.RENT)
			return new FlatRentAdvertComparator();
		throw new NotImplementedException();
	}
	
}
