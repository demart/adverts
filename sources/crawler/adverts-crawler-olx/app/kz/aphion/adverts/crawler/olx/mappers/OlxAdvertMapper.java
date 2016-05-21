package kz.aphion.adverts.crawler.olx.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.olx.OlxAdvertCategoryType;
import kz.aphion.adverts.crawler.olx.mappers.flat.FlatRentDataMapper;
import kz.aphion.adverts.crawler.olx.mappers.flat.FlatSellDataMapper;
import kz.aphion.adverts.persistence.realty.Realty;
import play.Logger;

/**
 * Класс мапер объявлений из Json Olx во внутренние объекты
 * 
 * @author artem.demidovich
 *
 * Created at May 20, 2016
 */
public class OlxAdvertMapper {

	public static List<Realty> extractAndConvertAdverts(Map<String, Object> jsonResponseMap) throws CrawlerException {
		if (!jsonResponseMap.containsKey("ads")) {
			if (Logger.isDebugEnabled())
				Logger.debug("Received data with empty adverts list. Completing process.");
			return null;
		}
		
		List<Map<String, Object>> rawAdverts = (List<Map<String, Object>>) jsonResponseMap.get("ads");
		if (Logger.isDebugEnabled())
			Logger.debug("Received " + rawAdverts.size() + " adverts to process");
		
		
		List<Realty> adverts = new ArrayList<Realty>();
		for (Map<String, Object> rawAdvert : rawAdverts) {
			if (Logger.isDebugEnabled())
				Logger.debug("Advert with Id: " + (Long)rawAdvert.get("id") + " will be converted");
			
			try {
				Realty realty = convertRealtyToAdvertEntity(rawAdvert);
				if (realty != null) {
					adverts.add(realty);
				} else {
					// NULL - это проблема, видимо что-то случилось
					Logger.error("ATTENTION: Advert with id [" + (Long)rawAdvert.get("id") + "] wasn't processed!");
				}
			} catch (Exception e) {
				Logger.error(e, "ATTENTION: Advert with id [" + rawAdvert.get("id") + "] wasn't processed because of errors!");
			}
		}
		
		return adverts;
		
	}
	
	
	private static Realty convertRealtyToAdvertEntity(Map<String, Object> rawAdvert) throws CrawlerException {
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
				Logger.info("Category with Id [%s] will be skipped, as we don't have mappers for it yet", categoryId.name());
				break;
		}
		
		return null;
	}

	
}
