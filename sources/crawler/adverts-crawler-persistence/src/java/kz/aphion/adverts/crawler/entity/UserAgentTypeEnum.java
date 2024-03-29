package kz.aphion.adverts.crawler.entity;

/**
 * Тип (применение) User-Agent'а
 * 
 * @author artem.demidovich
 *
 */
public enum UserAgentTypeEnum {
	
	/**
	 * Обыкновенный браузер
	 */
	BROWSER,
	
	/**
	 * Браузер на мобильных платформах
	 */
	MOBILE_BROWSER,
	
	/**
	 * Встроенные браузерные библиотеки используемые в мобильных приложениях
	 */
	MOBILE_PLATFORM,
	
}
