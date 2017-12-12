package kz.aphion.adverts.web.api.query;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import kz.aphion.adverts.persistence.adverts.AdvertOperationType;
import kz.aphion.adverts.web.api.exceptions.MissingRequiredParameterException;
import kz.aphion.adverts.web.api.models.AdvertType;

import org.apache.commons.lang.StringUtils;

public class SearchAdvertQuery {

	public SearchAdvertQuery() {
		this.parameters = new LinkedHashMap<String, List<String>>();
	}
	
	/**
	 * Тип объявлений
	 */
	public AdvertType type;
	
	/**
	 * Под тип
	 */
	public String subType;
	
	/**
	 * Вид операции (Продажа, Аренда и т.д.)
	 */
	public AdvertOperationType operationType;
	
	/**
	 * Сколько объявлений выводить
	 */
	public Integer limit = 25;
	
	/**
	 * С какой позиции считать
	 */
	public Integer offset = 0;
	
	/**
	 * В каком объеме данные необходимо вернуть
	 */
	public RecordDetalisationType recordDetalisation = RecordDetalisationType.DEFAULT;
	
	/**
	 * Список остальных параметов для каждого отельного подтипа
	 */
	public HashMap<String, List<String>> parameters;

	
	/**
	 * Проверяет наличие обязательных параметров
	 * @return
	 * @throws MissingRequiredParameterException 
	 */
	public void validateQuery() throws MissingRequiredParameterException {
		if (type == null || type == AdvertType.UNDEFINED)
			throw new MissingRequiredParameterException("AdvertType value is missing!");
		if (StringUtils.isBlank(subType))
			throw new MissingRequiredParameterException("AdvertSubType value is missing!");
		if (operationType == null || operationType == AdvertOperationType.UNDEFINED)
			throw new MissingRequiredParameterException("AdvertOperationType value is missing!");
	}
	
	
}
