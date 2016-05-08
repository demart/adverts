package kz.aphion.adverts.crawler.krisha;

/**
 * Типы категорий объявлений на сайте krisha.kz
 * 
 * @author artem.demidovich
 *
 */
public enum KrishaAdvertCategoryType {
	
	/**
	 * Продажа квартир
	 */
	SELL_APARTMENT(1),
	
	/**
	 * Аренда квартир
	 */
	RENT_APARTMENT(2),
	
	/**
	 * Продажа домов
	 */
	SELL_HOUSES(3),
	
	/**
	 * Арнеда домов
	 */
	RENT_HOUSES(4),
	
	/**
	 * Продажа дач
	 */
	SELL_DACHA(5),
	
	/**
	 * Аренда дач
	 */
	RENT_DACHA(6),
	
	/**
	 * Продажа зданий
	 */
	SELL_BUILDINGS(7),
	
	/**
	 * Аренда зданий
	 */
	RENT_BUILDING(8),
	
	/**
	 * Аренда комнат
	 */
	RENT_ROOMS(9),
	
	/**
	 * Продажа магазинов и бутиков
	 */
	SELL_SHOPS(10),
	
	/**
	 * Аренда магазинов и бутиков
	 */
	RENT_SHOPS(11),
	
	/**
	 * Продажа офисов
	 */
	SELL_OFFICES(12),
	
	/**
	 * Аренда офисов
	 */
	RENT_OFFICES(13),
	
	/**
	 * Продажа участков и земли
	 */
	SELL_LANDS(14),
	
	/**
	 * Продажа промбаз, складов и заводов
	 */
	SELL_INDUSTRIES(15),
	
	/**
	 * Аренда промбаз, складов и заводов
	 */
	RENT_INDUSTRIES(16),
	
	/**
	 * Продажа прочей недвижимости
	 */
	SELL_OTHERS(17),
	
	/**
	 * Арнеда прочей недвижимости
	 */
	RENT_OTHERS(18);
	
	
	private final int value;

	KrishaAdvertCategoryType(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
	
}
