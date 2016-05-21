package kz.aphion.adverts.crawler.olx;

import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.core.models.CrawlerModel;
import kz.aphion.adverts.crawler.core.models.CrawlerParameterModel;

import org.apache.commons.lang.StringUtils;

import play.Logger;

/**
 * Класс конструктор для построения URL запросов 
 * @author artem.demidovich
 *
 * Created at May 19, 2016
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
	 * Ключ для фильтрации по региону
	 */
	public final String FILTER_CITY_KEY = "CITY";	
	
	/**
	 * Ключ для фильтрации по комнатности от
	 */
	public final String FILTER_ROOMS_FROM_KEY = "ROOMS_FROM";
	
	/**
	 * Ключ для фильтрации по комнатности до
	 */
	public final String FILTER_ROOMS_TO_KEY = "ROOMS_TO";
	
	/**
	 * Ключ для извлечения URL для получения телефонов
	 */
	public final String FILTER_PHONE_URL_KEY = "PHONE_URL";	
	
	/**
	 * Название параметра для фильрации по категории
	 */
	public final String FILTER_PARAMETER_CATEGORY = "&search[category_id]=";
	
	/**
	 * Название параметра для фильрации по региону
	 */
	public final String FILTER_PARAMETER_REGION = "&search[region_id]=";
	
	/**
	 * Название параметра для фильрации по городу
	 */
	public final String FILTER_PARAMETER_CITY = "&search[city_id]=";
	
	/**
	 * Название параметра для фильрации по комнатности от
	 */
	public final String FILTER_PARAMETER_ROOM_FROM = "&search[filter_float_number_of_rooms:from]=";
	
	/**
	 * Название параметра для фильрации по комнатности до
	 */
	public final String FILTER_PARAMETER_ROOM_TO = "&search[filter_float_number_of_rooms:to]=";

	/**
	 * Название параметра для постраничной выгрузки
	 */
	public final String FILTER_PARAMETER_PAGE = "&page=";

	private boolean isPrepared = false;
	
	private String sourceSystemBaseUrl;
	private String sourceSystemBasePhoneUrl;
	private String filterParameterCategoryValue;
	private String filterParameterRegionValue;
	private String filterParameterCityValue;	
	private String filterParameterRoomFromValue;
	private String filterParameterRoomToValue;
	
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
			if (FILTER_REGION_KEY.equalsIgnoreCase(parameter.key)) {
				filterParameterRegionValue = parameter.value;
			}
			if (FILTER_CITY_KEY.equalsIgnoreCase(parameter.key)) {
				filterParameterCityValue = parameter.value;
			}
			if (FILTER_ROOMS_FROM_KEY.equalsIgnoreCase(parameter.key)) {
				filterParameterRoomFromValue = parameter.value;
			}
			if (FILTER_ROOMS_TO_KEY.equalsIgnoreCase(parameter.key)) {
				filterParameterRoomToValue = parameter.value;
			}
			if (FILTER_PHONE_URL_KEY.equalsIgnoreCase(parameter.key)) {
				sourceSystemBasePhoneUrl  = parameter.value;
			}
		}
		
		// Проверяем обязательные параметры на ввод
		if (StringUtils.isBlank(filterParameterCategoryValue))
			throw new CrawlerException("Crawler missing required field (or wrong value): " + FILTER_CATEGORY_KEY);
		
		// Проверяем если указаны сразу и регион и город (так как это противоречит логике работы сервиса)
		if (StringUtils.isNotBlank(filterParameterCityValue) && StringUtils.isNotBlank(filterParameterRegionValue))
			throw new CrawlerException("Crawler found 2 interchangeable options city and region: City [" + filterParameterCityValue + "] and Region [" + filterParameterRegionValue + "]");
		
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
		
		/*if (sourceSystemBaseUrl.contains("?")){
			b.append("&");
		} else {
			b.append("?");
		}*/
		
		b.append(FILTER_PARAMETER_CATEGORY);
		b.append(filterParameterCategoryValue);
		
		// Region
		if (!StringUtils.isBlank(filterParameterRegionValue)) {
			b.append(FILTER_PARAMETER_REGION);
			b.append(filterParameterRegionValue);
		}
		// City
		if (!StringUtils.isBlank(filterParameterCityValue)) {
			b.append(FILTER_PARAMETER_CITY);
			b.append(filterParameterCityValue);
		}
		
		// Room from
		if (!StringUtils.isBlank(filterParameterRoomFromValue)) {
			b.append(FILTER_PARAMETER_ROOM_FROM);
			b.append(filterParameterRoomFromValue);
		}

		// Room to		
		if (!StringUtils.isBlank(filterParameterRoomToValue)) {
			b.append(FILTER_PARAMETER_ROOM_TO);
			b.append(filterParameterRoomToValue);
		}
		
		// Paging parameter
		b.append(FILTER_PARAMETER_PAGE);
		b.append(page);
		
		// SORTING (IF POSSIBLE)
		//b.append("&orderBy[data][3][name]=add_date&orderBy[data][3][sort]=desc"); // KRISHA VERSION
		//b.append("&orderBy[system_data][3][name]=show_till&orderBy[system_data][3][sort]=desc"); // MY VERSION (HACKED)
		
		//if (Logger.isDebugEnabled())
		//	Logger.debug("UrlQueryBuilder: " + b.toString());
		Logger.info("UrlQueryBuilder: " + b.toString());
		
		return b.toString();
	}	
	
	
	/**
	 * Метод возвращает подготвленный url для получения телефонных номеров в объявлении
	 * @param advertId Идентификатор объявления
	 * @return
	 * @throws CrawlerException 
	 */
	public String buildPhoneQueryUrl(String advertId) throws CrawlerException {
		if (!isPrepared)
			throw new CrawlerException("QueryBuilder didn't prepare URL, please call prepareFilters first");
		StringBuilder b = new StringBuilder();
		b.append(sourceSystemBasePhoneUrl);
		b.append(advertId);
		return b.toString();
	}
	
	
}
