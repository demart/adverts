package kz.aphion.adverts.crawler.kn.mappers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import kz.aphion.adverts.crawler.core.CrawlerHttpClient;
import kz.aphion.adverts.crawler.core.DataManager;
import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.core.models.CrawlerModel;
import kz.aphion.adverts.crawler.core.models.UserAgentModel;
import kz.aphion.adverts.crawler.entity.UserAgentTypeEnum;
import kz.aphion.adverts.crawler.kn.mappers.flat.FlatRentDataMapper;
//import kz.aphion.adverts.crawler.kn.mappers.flat.FlatRentDataMapper;
import kz.aphion.adverts.crawler.kn.mappers.flat.FlatSellDataMapper;
import kz.aphion.adverts.crawler.kn.KnAdvertCategoryType;
import kz.aphion.adverts.crawler.kn.KnDataManager;
import kz.aphion.adverts.crawler.kn.QueryBuilder;
import kz.aphion.adverts.crawler.kn.entity.KnRegionEntity;
import kz.aphion.adverts.crawler.kn.jobs.KnCrawlerJob;
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
public class KnAdvertMapper {

	public static List<Realty> parseAdvertsFromCurrentPageAndConvert (KnAdvertCategoryType advertType, String content, QueryBuilder queryBuilder, CrawlerModel crawlerModel) throws CrawlerException, ParseException, IOException {
		//Список объявлений со страницы
		List<Realty> adverts = new ArrayList<Realty> ();		
		
		//Текущая страница
		Document page = Jsoup.parse(content);
		
		//Находим блок <ul class="result-list">, в котором лежат все объявления
		if (page.select("ul.results-list").size() > 0) {
			//Каждое объявление лежит в блоке <ul class="results-item">, тут соответственно мы берем все такие блоки
    		Elements parsedAdverts = page.select("ul.results-list").select("li.results-item");
    		
    		//(adverts.size() - 1), тут -1, так как на каждой странице есть блок рекламы.
    		Logger.info ("Count of adverts: " + (parsedAdverts.size() - 1));
    		
    		//Перебор и конвертирование объявлений
    		for (Element parsedAdvert : parsedAdverts) {
    			//Необходимая проверка из-за рекламного блока на каждой странице
    			if (!parsedAdvert.select("a[href]").isEmpty()) {
    				String advertUrl = queryBuilder.buildQueryUrlForAdvert(parsedAdvert.select("a[href]").first().attr("href").toString());
    				Realty realty = convertRealtyToAdvertEntity(advertType, advertUrl, queryBuilder, crawlerModel);
    				
    				if (realty != null) {
    					if (StringUtils.isBlank(realty.location.externalRegionId)) {
    						Logger.error("FOUND ADVERT [" + realty.source.externalAdvertId + "] WITHOUT REGION!!!");
    					} else {
    						//Logger.error("ADVERT [" + realty.source.externalAdvertId + "] REGION: [" + realty.location.externalRegionId + "]");
    						KnRegionEntity knRegionEntity = KnDataManager.getKnRegion(realty.location.externalRegionId);
    						if (knRegionEntity != null) {
    							realty.location.region = KnDataManager.getRegion(knRegionEntity.region);
    						    realty.location.regions = KnDataManager.getRegionsTree(knRegionEntity.region);
    						    
    						       						
    						} else {
    							// ПЛОХО ЧТО РЕГИОН НЕ НАШЛИ НУЖНО РУГАТЬСЯ
    						}
    					} 
    					if (realty.location.region == null) {
    						Logger.error("ATTENTION: Advert.Id [" + realty.source.externalAdvertId + "] Can't map kn geo id [" + realty.location.externalRegionId + "] in internal region dictionary.");
    					}
    					
    					adverts.add(realty);
    				}
    			}
    		}
    		
    		return adverts;
		}
		
		//Возвращаем пусто список, если блока с объявлениями не найдено
		return adverts;
	}
	
	private static Realty convertRealtyToAdvertEntity(KnAdvertCategoryType advertType, String advertUrl, QueryBuilder queryBuilder, CrawlerModel crawlerModel) throws ParseException, CrawlerException, IOException {
		
		//Получаем страницу объявления
		//TODO подумать как сделать по-другому
		//UserAgentModel uam = DataManager.getRandomUserAgent(UserAgentTypeEnum.BROWSER);
		//String content = CrawlerHttpClient.getContent(advertUrl, null, null, uam.userAgent);
		String content = KnCrawlerJob.callServerAndGetData(advertUrl, crawlerModel);		
		
		Realty realty = null;
		
		switch (advertType) {
			case SELL_APARTMENT:
				FlatSellDataMapper sellFlatMapper = new FlatSellDataMapper(new FlatSellRealty());
				realty = sellFlatMapper.mapAdvertObject(content, queryBuilder, advertType);
				break;
				
			case RENT_APARTMENT:
				FlatRentDataMapper rentFlatMapper = new FlatRentDataMapper(new FlatRentRealty());
				realty = rentFlatMapper.mapAdvertObject(content, queryBuilder, advertType);
				break;
				
			case RENT_APARTMENT_DAILY:
				FlatRentDataMapper rentFlatDailyMapper = new FlatRentDataMapper(new FlatRentRealty());
				realty = rentFlatDailyMapper.mapAdvertObject(content, queryBuilder, advertType);
				break;
		}
		
		return realty;
	}
}
