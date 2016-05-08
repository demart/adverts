package kz.aphion.adverts.crawler.entity;

/**
 * Тип прокси сервера
 * 
 * @author artem.demidovich
 *
 */
public enum ProxyServerTypeEnum {
	/**
	 * HTTP вызовы поддерживает
	 */
	HTTP,
	
	/**
	 * HTTPs вызовы поддерживает
	 */
	HTTPS,
	
	/**
	 * HTTP и HTTPs вызовы поддерживает
	 */
	BOTH,
}
