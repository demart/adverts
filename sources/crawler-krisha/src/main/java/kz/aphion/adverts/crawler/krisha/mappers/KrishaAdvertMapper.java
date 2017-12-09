package kz.aphion.adverts.crawler.krisha.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.krisha.KrishaAdvertCategoryType;
import kz.aphion.adverts.crawler.krisha.KrishaDataManager;
import kz.aphion.adverts.crawler.krisha.mappers.flat.FlatRentDataMapper;
import kz.aphion.adverts.crawler.krisha.mappers.flat.FlatSellDataMapper;
import kz.aphion.adverts.crawler.krisha.persistence.KrishaRegion;
import kz.aphion.adverts.persistence.adverts.Advert;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс конвертации объектов объявлений
 * 
 * @author artem.demidovich
 *
 */
public class KrishaAdvertMapper {

	private static Logger logger = LoggerFactory.getLogger(KrishaAdvertMapper.class);
	
	public static List<Advert> extractAndConvertAdverts(KrishaAdvertCategoryType advertType, Map<String, Object> jsonResponseMap) throws CrawlerException {
		if (!jsonResponseMap.containsKey("adverts")) {
			if (logger.isDebugEnabled())
				logger.debug("Received data with empty adverts list. Completing process.");
			return null;
		}
		
		List<Map<String, Object>> rawAdverts = (List<Map<String, Object>>) jsonResponseMap.get("adverts");
		if (logger.isDebugEnabled())
			logger.debug("Received " + rawAdverts.size() + " adverts to process");
		
		List<Advert> adverts = new ArrayList<Advert>();
		for (Map<String, Object> rawAdvert : rawAdverts) {
			if (logger.isDebugEnabled())
				logger.debug("Advert with Id: " + rawAdvert.get("id") + " will be converted");
			
			try {
				Advert realty = convertRealtyToAdvertEntity(advertType, rawAdvert);
				if (realty != null) {
					
					// Пробуем замапить внешние регионы на внутренние
					if (StringUtils.isBlank(realty.location.externalRegionId)) {
						logger.error("FOUND ADVERT [" + realty.source.externalId + "] WITHOUT REGION!!!");
					} else {
						//Logger.error("ADVERT [" + realty.source.externalAdvertId + "] REGION: [" + realty.location.externalRegionId + "]");
						KrishaRegion krishaRegionEntity = KrishaDataManager.getKrishaRegion(realty.location.externalRegionId);
						if (krishaRegionEntity != null) {
							//realty.location.region = KrishaDataManager.getRegion(krishaRegionEntity.region);
							//realty.location.regions = KrishaDataManager.getRegionsTree(krishaRegionEntity.region);
							realty.location.region = krishaRegionEntity.region;
							realty.location.regions = KrishaDataManager.getRegionsTree(krishaRegionEntity.region);
						} else {
							// ПЛОХО ЧТО РЕГИОН НЕ НАШЛИ НУЖНО РУГАТЬСЯ
						}
					}
					if (realty.location.region == null) {
						logger.error("ATTENTION: Advert.Id [" + realty.source.externalId + "] Can't map krisha geo id [" + realty.location.externalRegionId + "] in internal region dictionary.");
					}
					
					adverts.add(realty);
				} else {
					// NULL - это проблема, видимо что-то случилось
					logger.error("ATTENTION: Advert with id [" + rawAdvert.get("id") + "] wasn't processed!");
				}
			} catch (Exception e) {
				logger.error("ATTENTION: Advert with id [" + rawAdvert.get("id") + "] wasn't processed because of errors!", e);
			}
		}
		
		return adverts;
	}

	private static Advert convertRealtyToAdvertEntity(KrishaAdvertCategoryType advertType, Map<String, Object> rawAdvert) {
		// Пробежаться и выяснить тип
		KrishaAdvertCategoryType category = CommonMapperUtils.getAdvertCategoryType(rawAdvert);
		if (category == null)
			category = advertType;
		
		Advert realty = null;
		
		switch (advertType) {
			case SELL_APARTMENT:
				FlatSellDataMapper sellFlatMapper = new FlatSellDataMapper(new Advert());
				realty = sellFlatMapper.mapAdvertObject(rawAdvert);
				// TODO
				break;
			case RENT_APARTMENT:
				FlatRentDataMapper rentFlatMapper = new FlatRentDataMapper(new Advert());
				realty = rentFlatMapper.mapAdvertObject(rawAdvert);
				// TODO
				break;
			case SELL_HOUSES:
				// TODO
				break;
			case RENT_HOUSES:
				// TODO
				break;
			case SELL_DACHA:
				// TODO
				break;
			case RENT_DACHA:
				// TODO
				break;
			case SELL_BUILDINGS:
				// TODO
				break;
			case RENT_BUILDING:
				// TODO
				break;
			case RENT_ROOMS:
				// TODO
				break;
			case SELL_SHOPS:
				// TODO
				break;
			case RENT_SHOPS:
				// TODO
				break;
			case SELL_OFFICES:
				// TODO
				break;
			case RENT_OFFICES:
				break;
			case SELL_LANDS:
				break;
			case SELL_INDUSTRIES:
				break;
			case RENT_INDUSTRIES:
				break;
			case SELL_OTHERS:
				break;
			case RENT_OTHERS:
				break;
			default:
				break;
		}
		
		return realty;
	}
	
}
