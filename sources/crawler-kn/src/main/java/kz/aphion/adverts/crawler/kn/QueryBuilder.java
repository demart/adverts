package kz.aphion.adverts.crawler.kn;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.core.models.CrawlerModel;
import kz.aphion.adverts.crawler.core.models.CrawlerParameterModel;


/**
 * Класс для формирования запросов на выгрузку объявлений с kn.kz
 * 
 * @author denis.krylov
 *
 */
public class QueryBuilder {

	private static Logger logger = LoggerFactory.getLogger(QueryBuilder.class);
	
	/**
	 * Ключ для фильтрации по категории + количество комнат.
	 * На kn.kz задается количество комнат не в фильтре, а в категории.
	 * Пример: prodazha-kvartir ; prodazha-dvuhkomnatnyh-kvartir
	 */
	public final String FILTER_CATEGORY_KEY = "CATEGORY";
	
	/**
	 * Ключ для фильтрации по региону
	 */
	public final String FILTER_REGION_KEY = "REGION";

	
	private String sourceSystemBaseUrl;
	private String filterParameterCategoryValue;
	private String filterParameterRegionValue;
	
	private boolean isPrepared = false;
	
	/**
	 * Поиск всех необходимых параметров краулера для построение страницы
	 * @param model
	 * @throws CrawlerException 
	 */
	public QueryBuilder prepareFilters(CrawlerModel model) throws CrawlerException {
		if (StringUtils.isBlank(model.crawlerGroup.sourceSystemBaseUrl)) 
			throw new CrawlerException("CrawlerGroup sourceSystemBaseUrl isBlank.");
		
		sourceSystemBaseUrl = model.crawlerGroup.sourceSystemBaseUrl;
		
		if (model.parameters == null || model.parameters.size() == 0)
			throw new CrawlerException("Crawler parameters is null or empty. Missing required fields.");
		
		for (CrawlerParameterModel parameter : model.parameters) {
			if (logger.isDebugEnabled())
				logger.debug("Found crawler parameter: " + parameter.key + " with name: " + parameter.name + " and value: " + parameter.value);
		
			if (FILTER_CATEGORY_KEY.equalsIgnoreCase(parameter.key)) {
				filterParameterCategoryValue = parameter.value;
			}
			if (FILTER_REGION_KEY.equalsIgnoreCase(parameter.key)) {
				filterParameterRegionValue = parameter.value;
			}
		}
		
		// Проверяем обязательные параметры на ввод
		if (StringUtils.isBlank(filterParameterCategoryValue))
			throw new CrawlerException("Crawler missing required field (or wrong value): " + FILTER_CATEGORY_KEY);
		
		isPrepared = true;
		return this;
	}
	
	/**
	 * Строит URL для образащения к источнику информации
	 * @param offset Смещение по объявлениям (для постраничной прогрузки)
	 * @return
	 * @throws CrawlerException 
	 */
	public String buildQueryUrl(Integer page) throws CrawlerException {
		if (!isPrepared)
			throw new CrawlerException("QueryBuilder didn't prepare URL, please call prepareFilters first");
		
		StringBuilder b = new StringBuilder();
		b.append(sourceSystemBaseUrl);

		
		// Region
		if (!StringUtils.isBlank(filterParameterRegionValue)) {
			b.append("/");
			b.append(filterParameterRegionValue);
		}
		
		// Category
		if (!StringUtils.isBlank(filterParameterCategoryValue)) {
			b.append("/");
			b.append(filterParameterCategoryValue);
		}
		
		
		b.append("/order/sort_date/direction/desc/"); //Сортировка по новизне обновлений
		
		
		//Добавляем номер страницы
		b.append("page/" + page + "/");
		
		//if (Logger.isDebugEnabled())
		//	Logger.debug("UrlQueryBuilder: " + b.toString());
		logger.info("UrlQueryBuilder: " + b.toString());
		
		return b.toString();
	}
	
	/**
	 * Создание запроса для получение каждого объявления
	 * @param shortUrlToAdvert
	 * @return
	 * @throws CrawlerException
	 */
	public String buildQueryUrlForAdvert (String shortUrlToAdvert) throws CrawlerException {
		if (!isPrepared)
			throw new CrawlerException("QueryBuilder didn't prepare URL, please call prepareFilters first");
		
		StringBuilder b = new StringBuilder();
		
		//добавляем исходную ссылку на kn.kz
		b.append(sourceSystemBaseUrl);
		
		//добавляем короткую ссылку на объявление
		b.append(shortUrlToAdvert);
		
		logger.info("buildQueryUrlForAdver: " + b.toString());
		return b.toString();
				
	}
	
	/**
	 * Создание ссылок на картинки
	 * @param shortUrlToAdvert
	 * @return
	 * @throws CrawlerException
	 */
	public String buildQueryUrlForImage (String shortUrlToImage) throws CrawlerException {
		if (!isPrepared)
			throw new CrawlerException("QueryBuilder didn't prepare URL, please call prepareFilters first");
		
		StringBuilder b = new StringBuilder();
		
		//добавляем исходную ссылку на kn.kz
		b.append(sourceSystemBaseUrl);
		
		//добавляем короткую ссылку на объявление
		b.append(shortUrlToImage);
		
		//Logger.info("buildQueryUrlForAdver: " + b.toString());
		return b.toString();
				
	}
}
