package kz.aphion.adverts.crawler.irr;

import org.apache.commons.lang.StringUtils;

import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.core.models.CrawlerModel;
import kz.aphion.adverts.crawler.core.models.CrawlerParameterModel;
import play.Logger;

/**
 * Класс для формирования запросов на выгрузку объявлений с kn.kz
 * 
 * @author denis.krylov
 *
 */
public class QueryBuilder {
	
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
	
	/**
	 * Название параметра для смещение (постраничных проход)
	 */
	public final String FILTER_PARAMETER_OFFSET = "offset";
	
	/**
	 * Название параметра для лимита
	 */
	public final String FILTER_PARAMETER_LIMIT = "limit";
	
	/**
	 * Кол-во записей за раз
	 */
	public final String FILTER_LIMIT_KEY = "LIMIT";
	
	
	private String sourceSystemBaseUrl;
	private String filterParameterCategoryValue;
	private String filterParameterRegionValue;
	private Integer filterParameterLimitValue;
	
	private boolean isPrepared = false;
	
	/**
	 * Поиск всех необходимых парамтеров краулера для построение страницы
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
			if (Logger.isDebugEnabled())
				Logger.debug("Found crawler parameter: " + parameter.key + " with name: " + parameter.name + " and value: " + parameter.value);
		
			if (FILTER_CATEGORY_KEY.equalsIgnoreCase(parameter.key)) {
				filterParameterCategoryValue = parameter.value;
			}
			if (FILTER_LIMIT_KEY.equalsIgnoreCase(parameter.key)) {
				filterParameterLimitValue = Integer.parseInt(parameter.value);
			}
			if (FILTER_REGION_KEY.equalsIgnoreCase(parameter.key)) {
				filterParameterRegionValue = parameter.value;
			}

		}
		
		// Проверяем обязательные параметры на ввод
		if (StringUtils.isBlank(filterParameterCategoryValue))
			throw new CrawlerException("Crawler missing required field (or wrong value): " + FILTER_CATEGORY_KEY);
		if (filterParameterLimitValue == 0)
			throw new CrawlerException("Crawler missing required field (or wrong value): " + FILTER_LIMIT_KEY);
		
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

				
		// Category
		if (!StringUtils.isBlank(filterParameterCategoryValue)) {
			b.append("/advertisements/search?category=");
			b.append(filterParameterCategoryValue);
		}
		
		// Region
		if (!StringUtils.isBlank(filterParameterRegionValue)) {
			b.append("/&region=");
			b.append(filterParameterRegionValue);
		}
		
		//Сортировка по новизне обновлений
		b.append("/&sort_by=date_sort:desc&include_categories=true&");
		
		// Offset
		b.append("&");
		b.append(FILTER_PARAMETER_OFFSET);
		b.append("=");
		b.append(String.valueOf(page*filterParameterLimitValue));
		
		// Limit
		b.append("&");
		b.append(FILTER_PARAMETER_LIMIT);
		b.append("=");
		b.append(String.valueOf(filterParameterLimitValue));
		
		//Фильтры
		b.append("&filters=/search/?");
		
		//if (Logger.isDebugEnabled())
		//	Logger.debug("UrlQueryBuilder: " + b.toString());
		Logger.info("UrlQueryBuilder: " + b.toString());
		
		return b.toString();
	}
	
	/**
	 * Создание запроса для получение каждого объявления
	 * @param advertId
	 * @return
	 * @throws CrawlerException
	 */
	public String buildQueryUrlForAdvert (String advertId) throws CrawlerException {
		if (!isPrepared)
			throw new CrawlerException("QueryBuilder didn't prepare URL, please call prepareFilters first");
		
		StringBuilder b = new StringBuilder();
		
		//добавляем исходную ссылку на irr.kz
		b.append(sourceSystemBaseUrl);
		b.append("/advertisements/advert/");
		
		//добавляем номер объявления
		b.append(advertId);
		
		Logger.info("buildQueryUrlForAdvert: " + b.toString());
		return b.toString();
				
	}
}
