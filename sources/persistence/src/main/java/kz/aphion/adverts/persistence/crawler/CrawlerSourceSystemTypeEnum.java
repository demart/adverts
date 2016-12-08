package kz.aphion.adverts.persistence.crawler;

/**
 * Список систем источников (доноров)
 * Возможно лучше вынести в таблицу для большей динамики при создании груп Сrawler'ов. 
 * Но пока сойдет
 * 
 * @author artem.demidovich
 *
 */
public enum CrawlerSourceSystemTypeEnum {

	/**
	 * Не указано
	 */
	UNDEFINED,
	
	/**
	 * Крыша, http://krisha.kz
	 */
	KRISHA,
	
	/**
	 * КН, http://kn.kz
	 */
	KN,
	
	/**
	 * OLX, http://olx.kz
	 */
	OLX,
	
	/**
	 * Из рук в руки, http://irr.kz
	 */
	IRR,
	
	/**
	 * Колеса, http://kolesa.kz
	 */
	KOLESA,
	
}
