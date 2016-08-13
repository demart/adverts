package kz.aphion.adverts.subscription.searcher;

import kz.aphion.adverts.persistence.realty.types.RealtyOperationType;
import kz.aphion.adverts.persistence.realty.types.RealtyType;
import kz.aphion.adverts.subscription.mq.RealtyAnalyserToSubscriptionProcessModel;
import kz.aphion.adverts.subscription.searcher.impl.FlatRentSubscriptionSearcher;
import kz.aphion.adverts.subscription.searcher.impl.FlatSellSubscriptionSearcher;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Фабрика возвращает конкретный алгоритм поиска дубликатов по типу объявления
 * @author artem.demidovich
 *
 * Created at Jun 12, 2016
 */
public class SubscriptionSearcherFactory {

	private static Logger logger = LoggerFactory.getLogger(SubscriptionSearcherFactory.class);
	
	/**
	 * Возвращает подходящий эксемпляр искателя подписок недвижимости
	 * @param realy
	 * @return
	 */
	public static SubscriptionSearcher getRealtySubscriptionSearcher(RealtyAnalyserToSubscriptionProcessModel model) {
		logger.info("Invoking realty subscription search factory");
		SubscriptionSearcher searcher = null;
		if (model.type == RealtyType.FLAT && model.operation == RealtyOperationType.SELL) {
			 searcher = new FlatSellSubscriptionSearcher();
		}
		
		if (model.type == RealtyType.FLAT && model.operation == RealtyOperationType.RENT) {
			searcher = new FlatRentSubscriptionSearcher();
		}
		
		if (searcher != null) {
			searcher.setAdvertObjectId(model.advertId);
			return searcher;
		}
		
		throw new NotImplementedException();
	}
	
}
