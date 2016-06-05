package kz.aphion.adverts.crawler.irr.mappers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.core.models.CrawlerModel;
import kz.aphion.adverts.crawler.irr.IrrAdvertCategoryType;
import kz.aphion.adverts.crawler.irr.IrrDataManager;
import kz.aphion.adverts.crawler.irr.IrrJsonToMapParser;
import kz.aphion.adverts.crawler.irr.QueryBuilder;
import kz.aphion.adverts.crawler.irr.entity.IrrRegionEntity;
import kz.aphion.adverts.crawler.irr.jobs.IrrCrawlerJob;
import kz.aphion.adverts.crawler.irr.mappers.flat.FlatRentDataMapper;
import kz.aphion.adverts.crawler.irr.mappers.flat.FlatSellDataMapper;
import kz.aphion.adverts.persistence.realty.Realty;
import kz.aphion.adverts.persistence.realty.data.flat.FlatRentRealty;
import kz.aphion.adverts.persistence.realty.data.flat.FlatSellRealty;
import play.Logger;

/**
 * Класс конвертации объектов объявлений
 * 
 * @author artem.demidovich
 *
 */
public class IrrAdvertMapper {

	
	public static List<Realty> extractAndConvertAdverts(IrrAdvertCategoryType advertType, Map<String, Object> jsonResponseMap, QueryBuilder queryBuilder, CrawlerModel crawlerModel) throws CrawlerException, IOException {
		//если блок с объявлениями пустой, то нужно остановить цикл
		if (jsonResponseMap.get("advertisements").toString().length() < 5) {
			if (Logger.isDebugEnabled())
				Logger.debug("Received data with empty adverts list. Completing process.");
			return null;
		}
		
		List<Map<String, Object>> rawAdverts = (List<Map<String, Object>>) jsonResponseMap.get("advertisements");
		if (Logger.isDebugEnabled())
			Logger.debug("Received " + rawAdverts.size() + " adverts to process");
		
		List<Realty> adverts = new ArrayList<Realty>();
		for (Map<String, Object> rawAdvert : rawAdverts) {
			if (Logger.isDebugEnabled())
				Logger.debug("Advert with Id: " + (Long)rawAdvert.get("id") + " will be converted");
			
			//Неободимо делать доп запрос, чтобы забрать всю информацию по объявлению.
			//Так как при получение всего списка объявлений количество параметров очень маленькое
			String advertUrl = queryBuilder.buildQueryUrlForAdvert(rawAdvert.get("id").toString());
			
			// Выгружаем контент с источника
			String jsonContent = IrrCrawlerJob.callServerAndGetJsonData(advertUrl, crawlerModel);
			
			//Конвертируем каждое объявление
			Map<String, Object> advertInformation = IrrJsonToMapParser.convertJson(jsonContent);
			
			Map<String, Object> advert = (Map<String, Object>) advertInformation.get("advertisement");
			
			try {
				Realty realty = convertRealtyToAdvertEntity(advertType, advert);
				if (realty != null) {
					
					// Пробуем замапить внешние регионы на внутренние
					if (StringUtils.isBlank(realty.location.externalRegionId)) {
						Logger.error("FOUND ADVERT [" + realty.source.externalAdvertId + "] WITHOUT REGION!!!");
					} else {
						//Logger.error("ADVERT [" + realty.source.externalAdvertId + "] REGION: [" + realty.location.externalRegionId + "]");
						IrrRegionEntity irrRegionEntity = IrrDataManager.getIrrRegion(realty.location.externalRegionId);
						if (irrRegionEntity != null) {
							realty.location.region = IrrDataManager.getRegion(irrRegionEntity.region);
							realty.location.regions = IrrDataManager.getRegionsTree(irrRegionEntity.region);
						} else {
							// ПЛОХО ЧТО РЕГИОН НЕ НАШЛИ НУЖНО РУГАТЬСЯ
						}
					}
					if (realty.location.region == null) {
						Logger.error("ATTENTION: Advert.Id [" + realty.source.externalAdvertId + "] Can't map irr geo id [" + realty.location.externalRegionId + "] in internal region dictionary.");
					}
					
					adverts.add(realty);
				} else {
					// NULL - это проблема, видимо что-то случилось
					Logger.error("ATTENTION: Advert with id [" + (Long)rawAdvert.get("id") + "] wasn't processed!");
				}
				/*
				 GsonBuilder builder = new GsonBuilder();
				 builder.setPrettyPrinting();
				 Gson gson = builder.create();
				 Logger.warn("");
				 Logger.warn(gson. toJson(realty));
				*/
			}
			catch (Exception e) {
				Logger.error(e, "ATTENTION: Advert with id [" + Long.parseLong((String)rawAdvert.get("id")) + "] wasn't processed because of errors!");
			}
 	}
		
		return adverts;
	}

	private static Realty convertRealtyToAdvertEntity(IrrAdvertCategoryType advertType, Map<String, Object> rawAdvert) throws CrawlerException, ParseException {
		Realty realty = null;
		
		switch (advertType) {
			case SELL_APARTMENT:
				FlatSellDataMapper sellFlatMapper = new FlatSellDataMapper(new FlatSellRealty());
				realty = sellFlatMapper.mapAdvertObject(rawAdvert);
				break;
				
			case RENT_APARTMENT:
				FlatRentDataMapper rentFlatMapper = new FlatRentDataMapper(new FlatRentRealty());
				realty = rentFlatMapper.mapAdvertObject(rawAdvert);
				break;
		}
		
		return realty;
	}
	
}
