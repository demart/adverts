package kz.aphion.adverts.subscription.searcher.impl;

import java.util.List;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.persistence.adverts.Advert;
import kz.aphion.adverts.persistence.subscription.Subscription;
import kz.aphion.adverts.subscription.searcher.SubscriptionSearcher;
import kz.aphion.adverts.subscription.searcher.impl.utils.FlatSubscriptionSearcherQueryBuilder;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Класс отвечает за поиск дубликатов объявлений
 * @author artem.demidovich
 *
 * Created at Jun 12, 2016
 */
public class FlatSellSubscriptionSearcher implements SubscriptionSearcher {

	private static Logger logger = LoggerFactory.getLogger(FlatSellSubscriptionSearcher.class);
	
	private Advert realty;
	
	@Override
	public void setAdvert(Advert advert) {
		this.realty = advert;
	}

	@Override
	public Advert getAdvert() {
		return realty;
	}
	
	
	@Override
	public List<Subscription> search() {
		try {
			Datastore ds = DB.DS();
			
			List<Subscription> result = search(ds, realty);
			if (result != null)
				logger.debug("Found Flat Sell [" + result.size() +  "] subscriptions");
			
			return result;
			
		} catch (Exception ex) {
			logger.error("Error in searching flat sell subscriptions", ex);
			return null;
		}
	}
	
	private List<Subscription> search(Datastore ds, Advert realty) {
		Query<Subscription> q = FlatSubscriptionSearcherQueryBuilder.buidSellQuery(ds, realty);
		
		List<Subscription> result = q.asList();
		return result;
	}
	
	
}
