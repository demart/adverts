package kz.aphion.adverts.web.api.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import kz.aphion.adverts.web.api.exceptions.IncorrectParameterValueException;
import kz.aphion.adverts.web.api.models.AdvertType;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@RequestScoped
public class SearchAdvertsQueryParser {

	private static Logger logger = LoggerFactory.getLogger(SearchAdvertsQueryParser.class);
	
	/**
	 * Разбирает запрос и извлекает необходимые базовые параметры. как тип и лимитации.
	 * которые являются общими для всех
	 * @param uriInfo
	 * @return
	 * @throws IncorrectParameterValueException 
	 */
	public SearchAdvertQuery parseQuery(UriInfo uriInfo) throws IncorrectParameterValueException {
		SearchAdvertQuery query = new SearchAdvertQuery();
		
		MultivaluedMap<String, String> parameters = uriInfo.getQueryParameters();
		for (String parameterKey : parameters.keySet()) {
			
			for(String parameterValue : parameters.get(parameterKey))
				logger.info("Query param {} value {}", parameterKey, parameterValue);
			
			switch (parameterKey) {
				case SupportedQueryParams.ADVERT_TYPE:
					query.type = parseAdvertType(parameters.getFirst(SupportedQueryParams.ADVERT_TYPE));
					break;
					
				case SupportedQueryParams.ADVERT_SUB_TYPE:
					query.subType = parameters.getFirst(SupportedQueryParams.ADVERT_SUB_TYPE);
					break;
	
				case SupportedQueryParams.ADVERT_OPERATION_TYPE:
					query.operationType = parameters.getFirst(SupportedQueryParams.ADVERT_OPERATION_TYPE);
					break;
	
				case SupportedQueryParams.RECORD_DETALISATION:
					query.recordDetalisation = parseRecordDetalisationType(parameters.getFirst(SupportedQueryParams.RECORD_DETALISATION));
					break;
				case SupportedQueryParams.QUERY_LIMIT:
					try {
						query.limit = Integer.parseInt(parameters.getFirst(SupportedQueryParams.QUERY_LIMIT));
					} catch(NumberFormatException ex) {
						throw new IncorrectParameterValueException("Incorrect value for limit parameter");
					}
					break;
					
				case SupportedQueryParams.QUERY_OFFSET:
					try {
						query.offset = Integer.parseInt(parameters.getFirst(SupportedQueryParams.QUERY_OFFSET));
					} catch(NumberFormatException ex) {
						throw new IncorrectParameterValueException("Incorrect value for offset parameter");
					}
					break;
					
				default:
					if (query.parameters.containsKey(parameterKey)) {
						logger.warn("Found duplicate parameters, skipping them..");
					} else {
						// Здесь все остальные параметры набиваем в лист
						// У нас есть правило что массивы отправлются [1,2,3,4]
						List<String> parsedValues = parseParameterValues(parameters.getFirst(parameterKey)); 
						// Складываем в список параметров для дальнейшей обработки
						query.parameters.put(parameterKey, parsedValues);
					}
					 
					break;
			}
			
		}
		
		return query;
	}

	private List<String> parseParameterValues(String values) throws IncorrectParameterValueException {
		// Если пустое значение все равное передаем, так как может используется где-то как флаг
		if (StringUtils.isBlank(values)) {
			return new ArrayList<String>();
		}
		
		// Если начинаются с [X,X,V], то разбираем
		if (values.startsWith("[") && values.endsWith("]")) {
			try {
				String[] splitParameters = values.substring(1, values.length()-1).split(",");
				List<String> params = new ArrayList<String>();
				for (String splitValue : splitParameters)
					params.add(splitValue);
				return params;
			} catch (Exception ex) {
				throw new IncorrectParameterValueException("Incorrect format of parameter with value + values", ex);
			}
		}
		
		return Arrays.asList(values);
	}

	
	private RecordDetalisationType parseRecordDetalisationType(String recordType) throws IncorrectParameterValueException {
		if (StringUtils.isBlank(recordType))
			throw new IncorrectParameterValueException("Provided RecordDetalisationType is null");
		
		for (RecordDetalisationType recordDetalisationType : RecordDetalisationType.values()) {
			if (recordDetalisationType.equals(recordType.toUpperCase()))
				return recordDetalisationType;
		}
		
		throw new IncorrectParameterValueException("Provided RecordDetalisationType is not supported");
	}

	private AdvertType parseAdvertType(String type) throws IncorrectParameterValueException {
		if (StringUtils.isBlank(type))
			throw new IncorrectParameterValueException("Provided AdvertType is null");
		
		switch (type.toUpperCase()) {
			case "R":
				return AdvertType.REALTY;
			case "REALTY":
				return AdvertType.REALTY;
			case "A":
				return AdvertType.AUTO;
			case "AUTO":
				return AdvertType.AUTO;
			default:
				throw new IncorrectParameterValueException("Provided AdvertType is not supported");
		}
		
	}
}
