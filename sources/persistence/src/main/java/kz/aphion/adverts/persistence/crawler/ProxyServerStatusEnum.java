package kz.aphion.adverts.persistence.crawler;

/**
 * Статусы прокси серверов
 * 
 * @author artem.demidovich
 *
 */
public enum ProxyServerStatusEnum {

	/**
	 * Активен т.е. рабочий
	 */
	ACTIVE,
	
	/**
	 * Не активен, видимо что-то случилось с серверов. Может отключен или подписка закончилась
	 */
	NOT_ACTIVE,
	
	/**
	 * Прокси сервер удален
	 */
	DELETED,
	
}
