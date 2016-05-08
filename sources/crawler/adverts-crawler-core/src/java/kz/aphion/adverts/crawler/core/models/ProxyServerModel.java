package kz.aphion.adverts.crawler.core.models;

import java.util.Calendar;

import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.entity.ProxyServerAuthTypeEnum;
import kz.aphion.adverts.crawler.entity.ProxyServerEntity;
import kz.aphion.adverts.crawler.entity.ProxyServerStatusEnum;
import kz.aphion.adverts.crawler.entity.ProxyServerTypeEnum;

/**
 * Модель прокси сервера
 * 
 * @author artem.demidovich
 *
 */
public class ProxyServerModel {

	public Long id;
	
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
	
	
	public static ProxyServerModel buildFromEntity(ProxyServerEntity entity) throws CrawlerException {
		if (entity == null)
			throw new CrawlerException("Can't convert null entity to a model");
		
		ProxyServerModel model = new ProxyServerModel();
		
		model.authType = entity.getAuthType();
		model.description = entity.getDescription();
		model.host = entity.getHost();
		model.id = entity.getId();
		model.inactiveFrom = entity.getInactiveFrom();
		model.lastCheck = entity.getLastCheck();
		model.lastUsage = entity.getLastUsage();
		model.login = entity.getLogin();
		model.name = entity.getName();
		model.password = entity.getPassword();
		model.port = entity.getPort();
		model.status = entity.getStatus();
		model.type = entity.getType();
		model.usageCount = entity.getUsageCount();
		
		return model;
	}

}
