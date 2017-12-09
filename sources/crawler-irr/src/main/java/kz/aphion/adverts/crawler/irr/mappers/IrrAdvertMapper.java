package kz.aphion.adverts.crawler.irr.mappers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.core.models.CrawlerModel;
import kz.aphion.adverts.crawler.irr.IrrAdvertCategoryType;
import kz.aphion.adverts.crawler.irr.IrrDataManager;
import kz.aphion.adverts.crawler.irr.IrrJsonToMapParser;
import kz.aphion.adverts.crawler.irr.QueryBuilder;
import kz.aphion.adverts.crawler.irr.jobs.IrrCrawlerJob;
import kz.aphion.adverts.crawler.irr.mappers.flat.FlatRentDataMapper;
import kz.aphion.adverts.crawler.irr.mappers.flat.FlatSellDataMapper;
import kz.aphion.adverts.crawler.irr.persistence.IrrRegion;
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
public class IrrAdvertMapper {

	private static Logger logger = LoggerFactory.getLogger(IrrAdvertMapper.class);

	
	public static List<Advert> extractAndConvertAdverts(IrrAdvertCategoryType advertType, Map<String, Object> jsonResponseMap, QueryBuilder queryBuilder, CrawlerModel crawlerModel) throws Exception {
		//если блок с объявлениями пустой, то нужно остановить цикл
		if (jsonResponseMap.get("advertisements").toString().length() < 5) {
			if (logger.isDebugEnabled())
				logger.debug("Received data with empty adverts list. Completing process.");
			return null;
		}
		
		List<Map<String, Object>> rawAdverts = (List<Map<String, Object>>) jsonResponseMap.get("advertisements");
		if (logger.isDebugEnabled())
			logger.debug("Received " + rawAdverts.size() + " adverts to process");
		
		List<Advert> adverts = new ArrayList<Advert>();
		for (Map<String, Object> rawAdvert : rawAdverts) {
			if (logger.isDebugEnabled())
				logger.debug("Advert with Id: " + rawAdvert.get("id") + " will be converted");
			
			//Неободимо делать доп запрос, чтобы забрать всю информацию по объявлению.
			//Так как при получение всего списка объявлений количество параметров очень маленькое
			String advertUrl = queryBuilder.buildQueryUrlForAdvert(rawAdvert.get("id").toString());
			
			// Выгружаем контент с источника
			String jsonContent = IrrCrawlerJob.callServerAndGetJsonData(advertUrl, crawlerModel);
			
			//Конвертируем каждое объявление
			Map<String, Object> advertInformation = IrrJsonToMapParser.convertJson(jsonContent);
			
			Map<String, Object> advert = (Map<String, Object>) advertInformation.get("advertisement");
			
			try {
				Advert realty = convertRealtyToAdvertEntity(advertType, advert);
				if (realty != null) {
					
					// Пробуем замапить внешние регионы на внутренние
					if (StringUtils.isBlank(realty.location.externalRegionId)) {
						logger.error("FOUND ADVERT [" + realty.source.externalId + "] WITHOUT REGION!!!");
					} else {
						IrrRegion irrRegionEntity = IrrDataManager.getIrrRegion(realty.location.externalRegionId);
						
						if (irrRegionEntity != null) {
							realty.location.region = IrrDataManager.getRegion(irrRegionEntity.region);
							realty.location.regions = IrrDataManager.getRegionsTree(irrRegionEntity.region);
						} else {
							// ПЛОХО ЧТО РЕГИОН НЕ НАШЛИ НУЖНО РУГАТЬСЯ
						}
					}
					if (realty.location.region == null) {
						logger.error("ATTENTION: Advert.Id [" + realty.source.externalId + "] Can't map irr geo id [" + realty.location.externalRegionId + "] in internal region dictionary.");
					}
					
					adverts.add(realty);
				} else {
					// NULL - это проблема, видимо что-то случилось
					logger.error("ATTENTION: Advert with id [" + rawAdvert.get("id") + "] wasn't processed!");
				}
			}
			catch (Exception e) {
				logger.error("ATTENTION: Advert with id [" + rawAdvert.get("id") + "] wasn't processed because of errors!", e);
			}
		}
		
		return adverts;
	}

	private static Advert convertRealtyToAdvertEntity(IrrAdvertCategoryType advertType, Map<String, Object> rawAdvert) throws CrawlerException, ParseException {
		Advert realty = null;
		
		switch (advertType) {
			case SELL_APARTMENT:
				FlatSellDataMapper sellFlatMapper = new FlatSellDataMapper(new Advert());
				realty = sellFlatMapper.mapAdvertObject(rawAdvert);
				break;
				
			case RENT_APARTMENT:
				FlatRentDataMapper rentFlatMapper = new FlatRentDataMapper(new Advert());
				realty = rentFlatMapper.mapAdvertObject(rawAdvert);
				break;
		default:
			break;
		}
		
		return realty;
	}
	
}
