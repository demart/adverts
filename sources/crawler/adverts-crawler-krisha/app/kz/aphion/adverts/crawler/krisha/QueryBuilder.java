package kz.aphion.adverts.crawler.krisha;

import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.core.models.CrawlerModel;
import kz.aphion.adverts.crawler.core.models.CrawlerParameterModel;

import org.apache.commons.lang.StringUtils;

import play.Logger;

/**
 * Класс для формирования запросов на выгрузку объявлений с krisha.kz
 * 
 * @author artem.demidovich
 *
 */
public class QueryBuilder {

	/**
	 * Ключ для фильтрации по категории
	 */
	public final String FILTER_CATEGORY_KEY = "CATEGORY";
	
	/**
	 * Ключ для фильтрации по региону
	 */
	public final String FILTER_REGION_KEY = "REGION";
	
	/**
	 * Ключ для фильтрации по комнатности от
	 */
	public final String FILTER_ROOMS_FROM_KEY = "ROOMS_FROM";
	
	/**
	 * Ключ для фильтрации по комнатности до
	 */
	public final String FILTER_ROOMS_TO_KEY = "ROOMS_TO";
	
	/**
	 * Кол-во записей за раз
	 */
	public final String FILTER_LIMIT_KEY = "LIMIT";
	
	/**
	 * Название параметра для лимита
	 */
	public final String FILTER_PARAMETER_LIMIT = "limit";
	
	/**
	 * Название параметра для смещение (постраничных проход)
	 */
	public final String FILTER_PARAMETER_OFFSET = "offset";
	
	/**
	 * Профикс для категории
	 */
	public final String FILTER_PARAMETR_CATEGORY = "catId=";
	
	private String sourceSystemBaseUrl;
	private Integer filterParameterLimitValue;
	private String filterParameterCategoryValue;
	private String filterParameterRegionValue;
	private String filterParameterRoomFromValue;
	private String filterParameterRoomToValue;
	
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
			if (FILTER_ROOMS_FROM_KEY.equalsIgnoreCase(parameter.key)) {
				filterParameterRoomFromValue = parameter.value;
			}
			if (FILTER_ROOMS_TO_KEY.equalsIgnoreCase(parameter.key)) {
				filterParameterRoomToValue = parameter.value;
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
		if (sourceSystemBaseUrl.contains("?")){
			b.append("&");
		} else {
			b.append("?");
		}
		b.append(FILTER_PARAMETR_CATEGORY);
		b.append(filterParameterCategoryValue);
		
		
		// Limit
		b.append("&");
		b.append(FILTER_PARAMETER_LIMIT);
		b.append("=");
		b.append(String.valueOf(filterParameterLimitValue));
		
		// Offset
		b.append("&");
		b.append(FILTER_PARAMETER_OFFSET);
		b.append("=");
		b.append(String.valueOf(page*filterParameterLimitValue));
		
		// Region
		if (!StringUtils.isBlank(filterParameterRegionValue)) {
			b.append("&");
			b.append(filterParameterRegionValue);
		}
		
		// Room from
		if (!StringUtils.isBlank(filterParameterRoomFromValue)) {
			b.append("&");
			b.append(filterParameterRoomFromValue);
		}

		// Room to		
		if (!StringUtils.isBlank(filterParameterRoomToValue)) {
			b.append("&");
			b.append(filterParameterRoomToValue);
		}
		
		//b.append("&orderBy[data][3][name]=add_date&orderBy[data][3][sort]=desc"); // KRISHA VERSION
		b.append("&orderBy[system_data][3][name]=show_till&orderBy[system_data][3][sort]=desc"); // MY VERSION (HACKED)
		
		//if (Logger.isDebugEnabled())
		//	Logger.debug("UrlQueryBuilder: " + b.toString());
		Logger.info("UrlQueryBuilder: " + b.toString());
		
		return b.toString();
	}
	
}
