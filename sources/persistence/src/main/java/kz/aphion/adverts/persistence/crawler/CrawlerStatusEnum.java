package kz.aphion.adverts.persistence.crawler;

/**
 * Статусы состояния Crawler'а
 * 
 * @author artem.demidovich
 *
 */
public enum CrawlerStatusEnum {
	/**
	 * Активен
	 */
	ACTIVE,
	
	/**
	 * Не активен
	 */
	NOT_ACTIVE,
	
	/**
	 * Удален
	 */
	DELETED,
}
