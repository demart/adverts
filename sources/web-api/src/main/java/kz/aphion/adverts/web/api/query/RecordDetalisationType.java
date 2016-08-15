package kz.aphion.adverts.web.api.query;

/**
 * В каком объеме необходимо возвращать данные клиенту
 * @author artem.demidovich
 *
 * Created at Aug 14, 2016
 */
public enum RecordDetalisationType {

	/**
	 * По умолчанию
	 */
	DEFAULT,
	
	/**
	 * Сокращенная форма, например для листов
	 */
	SHORT,
	
	/**
	 * Расширенный режим со всеми необходимыми данными для клинетов
	 */
	EXTENDED,
	
	/**
	 * Все данные которые есть в репозитории (для клиентов нельзя)
	 */
	FULL,
	
}
