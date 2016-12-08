package kz.aphion.adverts.crawler.irr;

/**
 * Типы категорий объявлений на сайте kn.kz
 * 
 * @author denis.krylov
 *
 */

public enum IrrAdvertCategoryType {
	/**
	 * Продажа квартир
	 */
	SELL_APARTMENT("real-estate/apartments-sale"),
	
	/**
	 * Продажа новых квартир
	 */
	SELL_APARTMENT_NEW("real-estate/apartments-sale/new/"),
	
	
	/**
	 * Продажа вторичного рынка
	 */
	SELL_APARTMENT_SECONDARY("real-estate/apartments-sale/secondary/"),

	
	/**
	 * Аренда квартир
	 */
	RENT_APARTMENT("real-estate/rent/");
	
	private String value;
	
	IrrAdvertCategoryType (String value) {
		this.value = value;
		
	}
	
	public String getValue () {
		return value;
	}

	
	
}
