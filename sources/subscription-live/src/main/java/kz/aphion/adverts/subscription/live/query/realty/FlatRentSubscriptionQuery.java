package kz.aphion.adverts.subscription.live.query.realty;

import kz.aphion.adverts.persistence.adverts.Advert;
import kz.aphion.adverts.persistence.adverts.AdvertOperationType;
import kz.aphion.adverts.persistence.adverts.AdvertType;
import kz.aphion.adverts.persistence.realty.RealtyType;
import kz.aphion.adverts.subscription.live.SubscriptionQuery;
import kz.aphion.adverts.subscription.live.models.realty.flat.RealtyRentFlatSubscriptionCriteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * Класс отвечает за инициализацию подписки на аренду квартир
 * @author artem.demidovich
 *
 * Created at Aug 14, 2016
 */
public class FlatRentSubscriptionQuery  implements SubscriptionQuery {

	private static Logger logger = LoggerFactory.getLogger(FlatRentSubscriptionQuery.class);
	
	private boolean isQueryReady = false;
	
	private RealtyRentFlatSubscriptionCriteria query;
	
	@Override
	public void buidQuery(String query) {
		logger.debug("Initializing query from: {}", query);
		
		Gson gson = new Gson();
		this.query = gson.fromJson(query, RealtyRentFlatSubscriptionCriteria.class);
		
		isQueryReady = true;
		
		logger.debug("Query initialization finished");
	}

	@Override
	public boolean isQueryReady() {
		return isQueryReady;
	}

	@Override
	public boolean isAdvertBelongToQuery(Advert advert) throws Exception {
		logger.debug("Check advert with Id {}", advert.id);
		if ((advert.type != AdvertType.REALTY) || (advert.operation != AdvertOperationType.RENT) || (RealtyType.FLAT != RealtyType.valueOf(advert.subType) ) ) {
			logger.debug("Skip this advert, it's belongs to another advert type");
			return false;
		}
		
		// Если еще не знаем что ищем
		if (!isQueryReady)
			return false;
		
		return query.isBelongs(advert);
	}
	
}