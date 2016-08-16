package kz.aphion.adverts.subscription.live.query.realty;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.realty.data.flat.FlatSellRealty;
import kz.aphion.adverts.subscription.live.SubscriptionQuery;
import kz.aphion.adverts.subscription.live.models.realty.flat.RealtySellFlatSubscriptionCriteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;


/**
 * Класс отвечает за инициализацию подписки на продажу квартир
 * 
 * @author artem.demidovich
 *
 * Created at Aug 14, 2016
 */
public class FlatSellSubscriptionQuery implements SubscriptionQuery {

	private static Logger logger = LoggerFactory.getLogger(FlatSellSubscriptionQuery.class);
	
	private boolean isQueryReady = false;
	
	private RealtySellFlatSubscriptionCriteria query;
	
	@Override
	public void buidQuery(String query) {
		logger.debug("Initializing query from: {}", query);
		
		Gson gson = new Gson();
		this.query = gson.fromJson(query, RealtySellFlatSubscriptionCriteria.class);
		
		isQueryReady = true;
		
		logger.debug("Query initialization finished");
	}

	@Override
	public boolean isQueryReady() {
		return isQueryReady;
	}

	@Override
	public boolean isAdvertBelongToQuery(BaseEntity advert) throws Exception {
		logger.debug("Check advert with Id {}", advert.id);
		if (!(advert instanceof FlatSellRealty)) {
			logger.debug("Skip this advert, it's belongs to another advert type");
			return false;
		}
		
		// Если еще не знаем что ищем
		if (!isQueryReady)
			return false;
		
		return query.isBelongs((FlatSellRealty)advert);
	}
	
}