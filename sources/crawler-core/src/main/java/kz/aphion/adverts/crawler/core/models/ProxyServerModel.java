package kz.aphion.adverts.crawler.core.models;

import java.util.Calendar;

import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.persistence.crawler.ProxyServerAuthTypeEnum;
import kz.aphion.adverts.persistence.crawler.ProxyServer;
import kz.aphion.adverts.persistence.crawler.ProxyServerStatusEnum;
import kz.aphion.adverts.persistence.crawler.ProxyServerTypeEnum;

/**
 * Модель прокси сервера
 * 
 * @author artem.demidovich
 *
 */
public class ProxyServerModel {

	public String id;
	
	/**
	 * Название прокси сервера
	 */
	public String name;

	/**
	 * Описание прокси сервера
	 */
	public String description;
	
	/**
	 * Хост прокси сервера
	 */
	public String host;
	
	/**
	 * Порт прокси сервера
	 */
	public Integer port;
	
	/**
	 * Тип авторизации прокси сервера
	 */
	public ProxyServerAuthTypeEnum authType;
	
	/**
	 * логин авторизации прокси сервера
	 */
	public String login;
	
	/**
	 * Пароль прокси сервера
	 */
	public String password;

	/**
	 * Статус прокси сервера
	 */
	public ProxyServerStatusEnum status;
	
	/**
	 * Недоступность сервера с этого времени
	 */
	public Calendar inactiveFrom;
	
	/**
	 * Последная проверка прокси сервера
	 */
	public Calendar lastCheck;
	
	/**
	 * Последнее использование прокси сервера
	 */
	public Calendar lastUsage;
	
	/**
	 * Кол-во использований прокси сервера
	 */
	public Long usageCount;
	
	/**
	 * Тип прокси сервера
	 */
	public ProxyServerTypeEnum type;
	
	
	public static ProxyServerModel buildFromEntity(ProxyServer entity) throws CrawlerException {
		if (entity == null)
			throw new CrawlerException("Can't convert null entity to a model");
		
		ProxyServerModel model = new ProxyServerModel();
		
		model.authType = entity.authType;
		model.description = entity.description;
		model.host = entity.host;
		model.id = entity.id.toHexString();
		model.inactiveFrom = entity.inactiveFrom;
		model.lastCheck = entity.lastCheck;
		model.lastUsage = entity.lastUsage;
		model.login = entity.login;
		model.name = entity.name;
		model.password = entity.password;
		model.port = entity.port;
		model.status = entity.status;
		model.type = entity.type;
		model.usageCount = entity.usageCount;
		
		return model;
	}

}
