package kz.aphion.adverts.analyser.searcher.impl;

import java.util.List;

import kz.aphion.adverts.analyser.processors.RealtyAdvertAnalyserProcessor;
import kz.aphion.adverts.analyser.searcher.DuplicateSearcher;
import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.persistence.realty.RealtyAdvertStatus;
import kz.aphion.adverts.persistence.realty.data.flat.FlatSellRealty;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Класс отвечает за поиск дубликатов объявлений
 * @author artem.demidovich
 *
 * Created at Jun 12, 2016
 */
public class FlatSellAdvertDuplicateSearcher implements DuplicateSearcher {

	private static Logger logger = LoggerFactory.getLogger(RealtyAdvertAnalyserProcessor.class);
	
	@Override
	public void searchDuplicates(String advertId) {
		try {
			FlatSellRealty realty = getAdvertObject(advertId);
			if (realty == null) {
				logger.warn("Object with provided Id [" + advertId + "] not found. Possibly alredy exist more newer version.");
				return;
			}
			
			List<FlatSellRealty> similarRealties = getSimilarAdvertObjects(realty);
			logger.info("Found [" + similarRealties.size() + "] similar ojbects to compare");
		
		} catch (Exception ex) {
			logger.error("Error in searching duplicates", ex);
		}
	}

	
	
	private List<FlatSellRealty> getSimilarAdvertObjects(FlatSellRealty realty) throws Exception {
		Query q = DB.DS().createQuery(FlatSellRealty.class);
		
		q.field("status").equal(RealtyAdvertStatus.ACTIVE);
		
		q.field("data.rooms").equal(realty.data.rooms);
		
		q.field("price").greaterThan(realty.price - Math.round(realty.price * 0.10));
		q.field("price").lessThan(realty.price + Math.round(realty.price * 0.10));
		
		q.field("data.square").greaterThanOrEq(realty.data.square - 1);
		q.field("data.square").lessThanOrEq(realty.data.square + 1);
		
		return q.asList();
	}
	
	
	private FlatSellRealty getAdvertObject(String advertId) throws Exception {
		Query q = DB.DS().createQuery(FlatSellRealty.class);
		 
		q.field("status").equal(RealtyAdvertStatus.ACTIVE);
		q.field("id").equal(new ObjectId(advertId));
		 
		List<FlatSellRealty> result = q.asList();
		
		if (result.size() > 0)
			return result.get(0);
		return null;
		
	}
	
	
}
