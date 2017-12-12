package kz.aphion.adverts.analyser.searcher.impl;

import java.util.List;

import kz.aphion.adverts.analyser.processors.AdvertAnalyserProcessorImpl;
import kz.aphion.adverts.analyser.searcher.DuplicateSearcher;
import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.models.realty.FlatRentAdvertModel;
import kz.aphion.adverts.persistence.adverts.Advert;
import kz.aphion.adverts.persistence.adverts.AdvertStatus;

import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FlatRentAdvertDuplicateSearcher implements DuplicateSearcher {

	private static Logger logger = LoggerFactory.getLogger(AdvertAnalyserProcessorImpl.class);
	
	@Override
	public void searchDuplicates(Advert advert) {
		try {
			
			List<Advert> similarRealties = getSimilarAdvertObjects(advert);
			logger.info("Found [" + similarRealties.size() + "] similar ojbects to compare");
		
		} catch (Exception ex) {
			logger.error("Error in searching duplicates", ex);
		}
	}
	
	
	private List<Advert> getSimilarAdvertObjects(Advert realty) throws Exception {
		Query q = DB.DS().createQuery(Advert.class);
		
		q.field("status").equal(AdvertStatus.ACTIVE);
		
		q.field("data.rooms").equal(realty.data.get("rooms"));
		
		Long price = new FlatRentAdvertModel(realty).getDataModel().getPrice();
		q.field("price").greaterThan(price - Math.round(price * 0.10));
		q.field("price").lessThan(price + Math.round(price * 0.10));
		
		q.field("data.square").greaterThanOrEq(Float.valueOf((String)realty.data.get("square")) - 1);
		q.field("data.square").lessThanOrEq(Float.valueOf((String)realty.data.get("square")) + 1);
		
		return q.asList();
	}

}
