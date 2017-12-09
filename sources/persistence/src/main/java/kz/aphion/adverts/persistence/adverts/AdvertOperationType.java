package kz.aphion.adverts.persistence.adverts;

/**
 * Вид операции над объявлением
 * @author artem.demidovich
 *
 * Created at Dec 7, 2017
 */
public enum AdvertOperationType {

	/**
	 * Не указано, хотя такого быть не должно
	 */
	UNDEFINED,
	
	/**
	 * Продажа
	 */
	SELL,
	
	/**
	 * Аренда
	 */
	RENT,
	
	/**
	 * Обмен
	 */
	EXCHANGE,
	
}
