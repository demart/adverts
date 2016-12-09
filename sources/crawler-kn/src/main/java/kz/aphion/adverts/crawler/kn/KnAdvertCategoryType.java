package kz.aphion.adverts.crawler.kn;

/**
 * Типы категорий объявлений на сайте kn.kz
 * 
 * @author denis.krylov
 *
 */

public enum KnAdvertCategoryType {
	/**
	 * Продажа квартир
	 */
	SELL_APARTMENT("prodazha-kvartir"),
	
	/**
	 * Аренда квартир помесячно
	 */
	RENT_APARTMENT("arenda-kvartir"),
	
	/**
	 * Аренда квартир посуточно
	 */
	RENT_APARTMENT_DAILY("arenda-kvartir-posutochno"),
	
	/**
	 * Аренда комнат посуточно
	 */
	RENT_ROOMS("arenda-komnat");
	
	private String value;
	
	KnAdvertCategoryType (String value) {
		this.value = value;
		
	}
	
	public String getValue () {
		return value;
	}

	
	
}
