package kz.aphion.adverts.subscription.searcher;

import kz.aphion.adverts.persistence.adverts.Advert;
import kz.aphion.adverts.persistence.adverts.AdvertType;
import kz.aphion.adverts.persistence.realty.RealtyType;
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
	public static SubscriptionSearcher getAdvertSubscriptionSearcher(Advert advert) {
		logger.info("Invoking advert subscription search factory");

		if (advert.type == AdvertType.REALTY) {
			RealtyType subType = RealtyType.valueOf(advert.subType);
			
			switch (subType) {
				case FLAT:
					switch (advert.operation) {
						case SELL:
							return new FlatSellSubscriptionSearcher();
						case RENT:
							return new FlatRentSubscriptionSearcher();
						default:
							break;
					}
					break;
				default:
					break;
			}
		}
		
		throw new NotImplementedException();
	}
	
}
