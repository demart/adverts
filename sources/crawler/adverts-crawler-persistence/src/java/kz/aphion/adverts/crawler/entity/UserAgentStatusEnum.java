package kz.aphion.adverts.crawler.entity;

/**
 * Статус User-Agent
 * 
 * @author artem.demidovich
 *
 */
public enum UserAgentStatusEnum {
	
	/**
	 * Активен т.е. рабочий
	 */
	ACTIVE,
	
	/**
	 * Не активен. Может устарел.
	 * Или просто отключен.
	 */
	NOT_ACTIVE,
	
	/**
	 * Удален
	 */
	DELETED,
	
}
