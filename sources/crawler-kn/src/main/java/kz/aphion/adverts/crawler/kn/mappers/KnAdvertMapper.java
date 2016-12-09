package kz.aphion.adverts.crawler.kn.mappers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.core.models.CrawlerModel;
import kz.aphion.adverts.crawler.kn.KnAdvertCategoryType;
import kz.aphion.adverts.crawler.kn.KnDataManager;
import kz.aphion.adverts.crawler.kn.QueryBuilder;
import kz.aphion.adverts.crawler.kn.jobs.KnCrawlerJob;
import kz.aphion.adverts.crawler.kn.mappers.flat.FlatRentDataMapper;
import kz.aphion.adverts.crawler.kn.mappers.flat.FlatSellDataMapper;
import kz.aphion.adverts.crawler.kn.persistence.KnRegion;
import kz.aphion.adverts.persistence.realty.Realty;
import kz.aphion.adverts.persistence.realty.data.flat.FlatRentRealty;
import kz.aphion.adverts.persistence.realty.data.flat.FlatSellRealty;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс конвертации объектов объявлений
 * 
 * @author artem.demidovich
 *
 */
public class KnAdvertMapper {

	private static Logger logger = LoggerFactory.getLogger(KnAdvertMapper.class);
	
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
    		logger.info ("Count of adverts: " + (parsedAdverts.size() - 1));
    		
    		//Перебор и конвертирование объявлений
    		for (Element parsedAdvert : parsedAdverts) {
    			try {
    				
	    			//Необходимая проверка из-за рекламного блока на каждой странице
	    			if (!parsedAdvert.select("a[href]").isEmpty()) {
	    				String advertUrl = queryBuilder.buildQueryUrlForAdvert(parsedAdvert.select("a[href]").first().attr("href").toString());
	    				Realty realty = convertRealtyToAdvertEntity(advertType, advertUrl, queryBuilder, crawlerModel);
	    				
	    				if (realty != null) {
	    					if (StringUtils.isBlank(realty.location.externalRegionId)) {
	    						logger.error("FOUND ADVERT [" + realty.source.externalAdvertId + "] WITHOUT REGION!!!");
	    					} else {
	    						KnRegion knRegion = KnDataManager.getKnRegion(realty.location.externalRegionId);
	    						if (knRegion != null) {
	    							realty.location.region = KnDataManager.getRegion(knRegion.region);
	    						    realty.location.regions = KnDataManager.getRegionsTree(knRegion.region);
	    						    
	    						    adverts.add(realty);
	    						} else {
	    							// ПЛОХО ЧТО РЕГИОН НЕ НАШЛИ НУЖНО РУГАТЬСЯ
	    							logger.error("ATTENTION: Advert.Id [" + realty.source.externalAdvertId + "] Can't map kn geo id [" + realty.location.externalRegionId + "] in internal region dictionary.");
	    						}
	    					} 
	    				}
	    			}
	    			
    			} catch (Exception ex) {
    				logger.error("ERROR! Parse advert problem, Element: " + parsedAdvert, ex);
    			}
    		}
    		
    		return adverts;
		}
		
		//Возвращаем пусто список, если блока с объявлениями не найдено
		return adverts;
	}
	
	private static Realty convertRealtyToAdvertEntity(KnAdvertCategoryType advertType, String advertUrl, QueryBuilder queryBuilder, CrawlerModel crawlerModel) throws ParseException, CrawlerException, IOException, Exception {
		
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
				
			// TODO ADD RENT_ROOMS 
		}
		
		return realty;
	}
}
