package kz.aphion.adverts.analyser.searcher;

import kz.aphion.adverts.analyser.searcher.impl.FlatRentAdvertDuplicateSearcher;
import kz.aphion.adverts.analyser.searcher.impl.FlatSellAdvertDuplicateSearcher;
import kz.aphion.adverts.common.models.mq.realties.ProcessRealtyModel;
import kz.aphion.adverts.persistence.realty.data.flat.FlatRentRealty;
import kz.aphion.adverts.persistence.realty.data.flat.FlatSellRealty;
import kz.aphion.adverts.persistence.realty.types.RealtyOperationType;
import kz.aphion.adverts.persistence.realty.types.RealtyType;

import org.apache.commons.lang.NotImplementedException;

/**
 * Фабрика возвращает конкретный алгоритм поиска дубликатов по типу объявления
 * @author artem.demidovich
 *
 * Created at Jun 12, 2016
 */
public class DuplicateSearcherFactory {

	/**
	 * Возвращает подходящий эксемпляр искателя дубликатов
	 * @param realy
	 * @return
	 */
	public static DuplicateSearcher getDuplicateSearcherInstance(ProcessRealtyModel model) {
		if (model.type == RealtyType.FLAT && model.operation == RealtyOperationType.SELL)
			return new FlatSellAdvertDuplicateSearcher();
		if (model.type == RealtyType.FLAT && model.operation == RealtyOperationType.RENT)
			return new FlatRentAdvertDuplicateSearcher();
		throw new NotImplementedException();
	}
	
}
