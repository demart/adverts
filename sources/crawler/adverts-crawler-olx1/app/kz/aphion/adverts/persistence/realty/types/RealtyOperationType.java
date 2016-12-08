package kz.aphion.adverts.persistence.realty.types;

/**
 * Вид операции над недвижимостью
 * 
 * @author artem.demidovich
 *
 */
public enum RealtyOperationType {

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
