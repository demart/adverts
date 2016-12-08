package kz.aphion.adverts.persistence.crawler;

/**
 * Типы авторизации поддерживаемые в прокси серверах
 * 
 * @author artem.demidovich
 *
 */
public enum ProxyServerAuthTypeEnum {

	/**
	 * Без авторизации
	 */
	NONE,
	
	/**
	 * Автризация по логин паролю
	 */
	LOGIN_PASSWORD,
	
	/**
	 * Basic авторизация (если такая есть)
	 */
	BASIC,
	
}
