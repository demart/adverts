package kz.aphion.adverts.web.api.query;

/**
 * Параметры 
 * @author artem.demidovich
 *
 * Created at Aug 14, 2016
 */
public class SupportedQueryParams {

	/**
	 * Тип объявлений
	 */
	public static final String ADVERT_TYPE = "type";
	
	/**
	 * Подпит объявлений
	 */
	public static final String ADVERT_SUB_TYPE ="subtype";
	
	/**
	 * Тип операции объявления
	 */
	public static final String ADVERT_OPERATION_TYPE = "operation";
	
	/**
	 * С какой позиции выдавать результат
	 */
	public static final String QUERY_OFFSET = "offset";
	
	/**
	 * Cколько объявлений должно быть на странице
	 */
	public static final String QUERY_LIMIT = "limit";
	
	/**
	 * Параметр сообщает в каком виде нужно вернуть данные, в простом или в развернутом
	 */
	public static final String  RECORD_DETALISATION = "data";
	
}
