package kz.aphion.adverts.persistence.crawler;

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
