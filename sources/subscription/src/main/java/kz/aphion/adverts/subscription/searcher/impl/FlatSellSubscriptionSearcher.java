package kz.aphion.adverts.subscription.searcher.impl;

import java.util.List;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.realty.data.flat.FlatSellRealty;
import kz.aphion.adverts.persistence.subscription.Subscription;
import kz.aphion.adverts.subscription.processors.RealtyAdvertSubscriptionProcessor;
import kz.aphion.adverts.subscription.providers.MongoDbProvider;
import kz.aphion.adverts.subscription.searcher.SubscriptionSearcher;
import kz.aphion.adverts.subscription.searcher.impl.utils.FlatSubscriptionSearcherQueryBuilder;

import org.bson.types.ObjectId;
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

	private static Logger logger = LoggerFactory.getLogger(RealtyAdvertSubscriptionProcessor.class);
	
	
	private ObjectId advertId;
	
	private FlatSellRealty realty;
	
	@Override
	public void setAdvertObjectId(String objectId) {
		this.advertId = new ObjectId(objectId);
	}

	@Override
	public BaseEntity getAdvertObject() {
		return realty;
	}
	
	
	@Override
	public List<Subscription> search() {
		try {
			Datastore ds = MongoDbProvider.getInstance().getDatastore();
			
			realty = ds.get(FlatSellRealty.class, advertId);
			if (realty == null) {
				logger.warn("Object with provided Id [" + advertId + "] not found. Possibly already exists newer version.");
				return null;
			}
			
			List<Subscription> result = search(ds, realty);
			if (result != null)
				logger.debug("Found Flat Sell [" + result.size() +  "] subscriptions");
			
			return result;
			
		} catch (Exception ex) {
			logger.error("Error in searching flat sell subscriptions", ex);
			return null;
		}
	}
	
	private List<Subscription> search(Datastore ds, FlatSellRealty realty) {
		Query<Subscription> q = FlatSubscriptionSearcherQueryBuilder.buidQuery(ds, realty);
		
		List<Subscription> result = q.asList();
		return result;
	}
	
	
}
