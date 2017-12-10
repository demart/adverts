package kz.aphion.adverts.crawler.olx.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.olx.OlxAdvertCategoryType;
import kz.aphion.adverts.crawler.olx.QueryBuilder;
import kz.aphion.adverts.crawler.olx.mappers.flat.FlatRentDataMapper;
import kz.aphion.adverts.crawler.olx.mappers.flat.FlatSellDataMapper;
import kz.aphion.adverts.persistence.adverts.Advert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс мапер объявлений из Json Olx во внутренние объекты
 * 
 * @author artem.demidovich
 *
 * Created at May 20, 2016
 */
public class OlxAdvertMapper {

	private static Logger logger = LoggerFactory.getLogger(QueryBuilder.class);

	public static List<Advert> extractAndConvertAdverts(Map<String, Object> jsonResponseMap) throws CrawlerException {
		if (!jsonResponseMap.containsKey("ads")) {
			if (logger.isDebugEnabled())
				logger.debug("Received data with empty adverts list. Completing process.");
			return null;
		}
		
		List<Map<String, Object>> rawAdverts = (List<Map<String, Object>>) jsonResponseMap.get("ads");
		if (logger.isDebugEnabled())
			logger.debug("Received " + rawAdverts.size() + " adverts to process");
		
		
		List<Advert> adverts = new ArrayList<Advert>();
		for (Map<String, Object> rawAdvert : rawAdverts) {
			if (logger.isDebugEnabled())
				logger.debug("Advert with Id: " + rawAdvert.get("id") + " will be converted");
			
			try {
				Advert realty = convertRealtyToAdvertEntity(rawAdvert);
				if (realty != null) {
					adverts.add(realty);
				} else {
					// NULL - это проблема, видимо что-то случилось
					logger.error("ATTENTION: Advert with id [" + (Long)rawAdvert.get("id") + "] wasn't processed!");
				}
			} catch (Exception e) {
				logger.error("ATTENTION: Advert with id [" + rawAdvert.get("id") + "] wasn't processed because of errors!", e);
			}
		}
		
		return adverts;
		
	}
	
	
	private static Advert convertRealtyToAdvertEntity(Map<String, Object> rawAdvert) throws CrawlerException {
		if (!rawAdvert.containsKey("category_id"))
			throw new CrawlerException("Can't find category_id parameter in olx raw advert");
		
		OlxAdvertCategoryType categoryId = CommonMapperUtils.parseAdvertCategoryType((String)rawAdvert.get("category_id"));
		
		switch (categoryId) {
			case REAL_ESTATE_SELL_FLAT:
			case REAL_ESTATE_SELL_FLAT_NEW:
			case REAL_ESTATE_SELL_FLAT_USED:
				return FlatSellDataMapper.mapAdvertObject(rawAdvert);
				
			case REAL_ESTATE_RENT_FLAT:
			case REAL_ESTATE_RENT_FLAT_DAILY:
			case REAL_ESTATE_RENT_FLAT_HOURLY:
			case REAL_ESTATE_RENT_FLAT_LONG_TERM:
				return FlatRentDataMapper.mapAdvertObject(rawAdvert);
				
			default:
				logger.info("Category with Id [{}] will be skipped, as we don't have mappers for it yet", categoryId.name());
				break;
		}
		
		return null;
	}

	
}
